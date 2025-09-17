package dev.alejo.bookpedia.book.data.repository

import dev.alejo.bookpedia.book.data.mappers.toBook
import dev.alejo.bookpedia.book.data.network.RemoteBookDataSource
import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.book.domain.repository.BookRepository
import dev.alejo.bookpedia.core.domain.DataError
import dev.alejo.bookpedia.core.domain.Result
import dev.alejo.bookpedia.core.domain.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query = query)
            .map { response ->
                response.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError.Remote> {
        return remoteBookDataSource
            .getBookDescription(bookWorkId = bookWorkId)
            .map {
                it.description
            }
    }

}