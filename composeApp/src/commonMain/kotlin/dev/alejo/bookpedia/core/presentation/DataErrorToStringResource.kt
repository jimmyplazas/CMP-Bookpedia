package dev.alejo.bookpedia.core.presentation

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.disk_full
import cmp_bookpedia.composeapp.generated.resources.no_internet
import cmp_bookpedia.composeapp.generated.resources.request_timeout
import cmp_bookpedia.composeapp.generated.resources.serialization
import cmp_bookpedia.composeapp.generated.resources.server_error
import cmp_bookpedia.composeapp.generated.resources.too_many_requests
import cmp_bookpedia.composeapp.generated.resources.unknown_error
import dev.alejo.bookpedia.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Local.DISK_FULL -> Res.string.disk_full
        DataError.Local.UNKNOWN -> Res.string.unknown_error
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.no_internet
        DataError.Remote.SERVER_ERROR -> Res.string.server_error
        DataError.Remote.SERIALIZATION -> Res.string.serialization
        DataError.Remote.UNKNOWN_ERROR -> Res.string.unknown_error
    }
    return UiText.StringResourceId(id = stringRes)
}