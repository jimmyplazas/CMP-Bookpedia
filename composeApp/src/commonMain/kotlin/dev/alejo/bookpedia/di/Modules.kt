package dev.alejo.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.alejo.bookpedia.book.data.database.DatabaseFactory
import dev.alejo.bookpedia.book.data.database.FavouriteBookDatabase
import dev.alejo.bookpedia.book.data.network.KtorRemoteBookDataSource
import dev.alejo.bookpedia.book.data.network.RemoteBookDataSource
import dev.alejo.bookpedia.book.data.repository.BookRepositoryImpl
import dev.alejo.bookpedia.book.domain.repository.BookRepository
import dev.alejo.bookpedia.book.presentation.SelectedBookViewModel
import dev.alejo.bookpedia.book.presentation.book_detail.BookDetailViewModel
import dev.alejo.bookpedia.book.presentation.book_list.BookListViewModel
import dev.alejo.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavouriteBookDatabase>().dao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}