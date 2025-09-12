package dev.alejo.bookpedia

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.book.presentation.book_list.BookListScreen
import dev.alejo.bookpedia.book.presentation.book_list.BookListState
import dev.alejo.bookpedia.book.presentation.book_list.components.BookListItem
import dev.alejo.bookpedia.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
private fun BookSearchBarPreview() {
    MaterialTheme {
        BookSearchBar(
            searchQuery = "Kotlin",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun BookItemPreview() {
    BookListItem(
        book = Book(
            id = "1",
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
        ),
        onClick = {}
    )
}

@Preview
@Composable
private fun BookScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = emptyList()
        ),
        onAction = {},
        modifier = Modifier.fillMaxWidth()
    )
}