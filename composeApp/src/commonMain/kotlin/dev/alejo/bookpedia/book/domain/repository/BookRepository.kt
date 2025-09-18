package dev.alejo.bookpedia.book.domain.repository

import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.core.domain.DataError
import dev.alejo.bookpedia.core.domain.EmptyResult
import dev.alejo.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>

    fun getFavouriteBooks(): Flow<List<Book>>
    fun isBookFavourite(bookId: String): Flow<Boolean>
    suspend fun markAsFavourite(book: Book) : EmptyResult<DataError.Local>
    suspend fun deleteFromFavourite(bookId: String)
}