package dev.alejo.bookpedia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.alejo.bookpedia.book.data.network.KtorRemoteBookDataSource
import dev.alejo.bookpedia.book.data.repository.BookRepositoryImpl
import dev.alejo.bookpedia.book.presentation.book_list.BookListScreenRoot
import dev.alejo.bookpedia.book.presentation.book_list.BookListViewModel
import dev.alejo.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    BookListScreenRoot(
        viewModel = remember {
            BookListViewModel(
                bookRepository = BookRepositoryImpl(
                    remoteBookDataSource = KtorRemoteBookDataSource(
                        httpClient = HttpClientFactory.create(
                            engine = engine
                        )
                    )
                )

            )
        },
        onBookCLick = {

        }
    )
}