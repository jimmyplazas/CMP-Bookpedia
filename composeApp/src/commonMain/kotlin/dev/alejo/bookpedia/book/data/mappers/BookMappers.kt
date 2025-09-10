package dev.alejo.bookpedia.book.data.mappers

import dev.alejo.bookpedia.book.data.dto.SearchedBookDto
import dev.alejo.bookpedia.book.domain.model.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = null,
        imageUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        languages = languages ?: emptyList(),
        firstPublicYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}