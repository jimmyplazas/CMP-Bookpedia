package dev.alejo.bookpedia.book.data.network

import dev.alejo.bookpedia.book.data.dto.SearchedResponseDto
import dev.alejo.bookpedia.core.data.safeCall
import dev.alejo.bookpedia.core.domain.DataError
import dev.alejo.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL= "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchedResponseDto, DataError.Remote> = safeCall {
        httpClient.get(
            urlString = "$BASE_URL/search.json"
        ) {
            parameter("q", query)
            parameter("limit", resultLimit)
            parameter("parameter", "eng")
            parameter(
                "fields", "key,title," +
                    "language," +
                    "cover_i,author_key," +
                    "author_name," +
                    "cover_edition_key," +
                    "first_publish_year," +
                    "ratings_average," +
                    "ratings_count," +
                    "number_of_pages_median," +
                    "edition_count"
            )
        }
    }
}