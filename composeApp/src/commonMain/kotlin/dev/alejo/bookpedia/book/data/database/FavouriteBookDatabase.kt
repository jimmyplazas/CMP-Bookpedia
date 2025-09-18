package dev.alejo.bookpedia.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavouriteBookDatabase : RoomDatabase() {
    abstract val dao: FavouriteBookDao

    companion object {
        const val DATABASE_NAME = "favourite_books_db"
    }
}