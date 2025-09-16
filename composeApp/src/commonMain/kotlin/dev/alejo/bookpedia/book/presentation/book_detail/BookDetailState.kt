package dev.alejo.bookpedia.book.presentation.book_detail

import dev.alejo.bookpedia.book.domain.model.Book

data class BookDetailState(
    val book: Book? = null,
    val isFavourite: Boolean = false,
    val isLoading: Boolean = false
)