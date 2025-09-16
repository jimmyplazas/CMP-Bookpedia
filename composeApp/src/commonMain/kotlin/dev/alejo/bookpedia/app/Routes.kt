package dev.alejo.bookpedia.app

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable
    data object BookGraph : Routes
    @Serializable
    data object BookList : Routes
    @Serializable
    data class BookDetail(val id: String) : Routes
}