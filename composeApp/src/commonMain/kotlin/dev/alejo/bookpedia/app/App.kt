package dev.alejo.bookpedia.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.alejo.bookpedia.book.presentation.SelectedBookViewModel
import dev.alejo.bookpedia.book.presentation.book_detail.BookDetailAction
import dev.alejo.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import dev.alejo.bookpedia.book.presentation.book_detail.BookDetailViewModel
import dev.alejo.bookpedia.book.presentation.book_list.BookListScreenRoot
import dev.alejo.bookpedia.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.BookGraph
    ) {
        navigation<Routes.BookGraph>(
            startDestination = Routes.BookList
        ) {
            composable<Routes.BookList>(
                exitTransition = { slideOutHorizontally() },
                popEnterTransition = {
                    slideInHorizontally()
                }
            ) {
                val viewModel = koinViewModel<BookListViewModel>()
                val selectedViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                LaunchedEffect(true) {
                    selectedViewModel.onSelectedBook(null)
                }

                BookListScreenRoot(
                    viewModel = viewModel,
                    onBookCLick = { book ->
                        selectedViewModel.onSelectedBook(book)
                        navController.navigate(Routes.BookDetail(book.id))
                    }
                )
            }
            composable<Routes.BookDetail>(
                enterTransition = {
                    slideInHorizontally { initialOffset ->
                        initialOffset
                    }
                },
                exitTransition = {
                    slideOutHorizontally { initialOffset ->
                        initialOffset
                    }
                }
            ) {
                val selectedViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                val viewModel = koinViewModel<BookDetailViewModel>()
                val selectedBook by selectedViewModel.selectedBook.collectAsStateWithLifecycle()

                LaunchedEffect(selectedBook) {
                    selectedBook?.let { book ->
                        viewModel.onAction(BookDetailAction.OnSelectedBookChange(book))
                    }
                }

                BookDetailScreenRoot(
                    viewModel = viewModel,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry,
    )
}