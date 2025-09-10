package dev.alejo.bookpedia.book.presentation.book_list

import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = dummyBooks,
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

val dummyBooks = (1..100).map {
    Book(
        id = it.toString(),
        title = "Hey",
        imageUrl = "asdasdasd",
        authors = listOf("asdasd", "sadfsdf"),
        description = "asdasdasdasdasdas",
        languages = listOf("es", "en"),
        firstPublicYear = "asdasd",
        averageRating = 3.5,
        ratingCount = 3,
        numPages = 200,
        numEditions = 3
    )
}