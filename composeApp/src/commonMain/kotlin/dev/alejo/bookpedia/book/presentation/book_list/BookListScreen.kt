package dev.alejo.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favourites
import cmp_bookpedia.composeapp.generated.resources.no_favourites
import cmp_bookpedia.composeapp.generated.resources.no_results
import cmp_bookpedia.composeapp.generated.resources.search_results
import dev.alejo.bookpedia.book.domain.model.Book
import dev.alejo.bookpedia.book.presentation.book_list.components.BookList
import dev.alejo.bookpedia.book.presentation.book_list.components.BookSearchBar
import dev.alejo.bookpedia.core.presentation.DarkBlue
import dev.alejo.bookpedia.core.presentation.DesertWhite
import dev.alejo.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookCLick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> onBookCLick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun BookListScreen(
    modifier: Modifier,
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResultsListState = rememberLazyListState()
    val favouriteBooksListState = rememberLazyListState()
    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favourites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }

                HorizontalPager(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    state = pagerState,
                    pageContent = { pageIndex ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (pageIndex) {
                                0 -> {
                                    if (state.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        when {
                                            state.errorMessage != null -> {
                                                Text(
                                                    text = state.errorMessage.asString(),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }

                                            state.searchResults.isEmpty() -> {
                                                Text(text = stringResource(Res.string.no_results))
                                            }

                                            else -> {
                                                BookList(
                                                    books = state.searchResults,
                                                    scrollState = searchResultsListState,
                                                    modifier = Modifier.fillMaxSize(),
                                                    onBookClick = { book ->
                                                        onAction(BookListAction.OnBookClick(book))
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    if (state.favoriteBooks.isEmpty()) {
                                        Text(text = stringResource(Res.string.no_favourites))
                                    } else {
                                        BookList(
                                            books = state.favoriteBooks,
                                            scrollState = favouriteBooksListState,
                                            modifier = Modifier.fillMaxSize(),
                                            onBookClick = { book ->
                                                onAction(BookListAction.OnBookClick(book))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}