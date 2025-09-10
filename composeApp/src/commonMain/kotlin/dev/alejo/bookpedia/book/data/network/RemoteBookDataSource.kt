package dev.alejo.bookpedia.book.data.network

import dev.alejo.bookpedia.book.data.dto.SearchedResponseDto
import dev.alejo.bookpedia.core.domain.DataError
import dev.alejo.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ) : Result<SearchedResponseDto, DataError.Remote>
}