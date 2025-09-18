package dev.alejo.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import dev.alejo.bookpedia.book.data.database.FavouriteBookDao
import dev.alejo.bookpedia.book.data.mappers.toBook
import dev.alejo.bookpedia.book.data.mappers.toBookEntity
import dev.alejo.bookpedia.book.data.network.RemoteBookDataSource
import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.book.domain.repository.BookRepository
import dev.alejo.bookpedia.core.domain.DataError
import dev.alejo.bookpedia.core.domain.EmptyResult
import dev.alejo.bookpedia.core.domain.Result
import dev.alejo.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favouriteBookDao: FavouriteBookDao
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query = query)
            .map { response ->
                response.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError.Remote> {
        val localResult = favouriteBookDao.getFavouriteBookById(bookWorkId)
        return if(localResult == null) {
            remoteBookDataSource
                .getBookDescription(bookWorkId = bookWorkId)
                .map {
                    it.description
                }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavouriteBooks(): Flow<List<Book>> {
        return favouriteBookDao
            .getFavouriteBooks()
            .map { bookEntities ->
                bookEntities.map{ bookEntity ->
                    bookEntity.toBook()
                }
            }
    }

    override fun isBookFavourite(bookId: String): Flow<Boolean> {
        return favouriteBookDao
            .getFavouriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == bookId }
            }
    }

    override suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favouriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavourite(bookId: String) {
        favouriteBookDao.deleteFavouriteBook(bookId)
    }

}