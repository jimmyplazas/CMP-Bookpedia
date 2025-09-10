package dev.alejo.bookpedia.core.domain

sealed interface DataError : Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN_ERROR
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}