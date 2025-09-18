package dev.alejo.bookpedia.book.presentation.book_list

import dev.alejo.bookpedia.book.domain.model.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val book: Book) : BookListAction
    data class OnTabSelected(val index: Int) : BookListAction
}