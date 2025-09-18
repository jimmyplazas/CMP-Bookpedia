package dev.alejo.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.alejo.bookpedia.app.Routes
import dev.alejo.bookpedia.book.domain.repository.BookRepository
import dev.alejo.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Routes.BookDetail>().id

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDescription()
            observeFavouriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> {

            }

            BookDetailAction.OnFavouriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavourite) {
                        bookRepository.deleteFromFavourite(bookId)
                    } else {
                        state.value.book?.let {
                            bookRepository.markAsFavourite(it)
                        }
                    }
                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(book = action.book)
                }
            }
        }
    }

    private fun observeFavouriteStatus() {
        bookRepository.isBookFavourite(bookId)
            .onEach { isFavourite ->
                _state.update {
                    it.copy(isFavourite = isFavourite)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(description = description),
                            isLoading = false
                        )
                    }
                }
        }
    }
}