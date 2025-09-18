package dev.alejo.bookpedia.book.presentation.book_detail

import dev.alejo.bookpedia.book.domain.model.Book

sealed interface BookDetailAction {
    data object OnBackClick : BookDetailAction
    data object OnFavouriteClick : BookDetailAction
    data class OnSelectedBookChange(val book: Book) : BookDetailAction
}