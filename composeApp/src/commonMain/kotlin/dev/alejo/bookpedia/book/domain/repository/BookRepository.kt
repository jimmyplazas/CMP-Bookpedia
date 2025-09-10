package dev.alejo.bookpedia.book.domain.repository

import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.core.domain.DataError
import dev.alejo.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}