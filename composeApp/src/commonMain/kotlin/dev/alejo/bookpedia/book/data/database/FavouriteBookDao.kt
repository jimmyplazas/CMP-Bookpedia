package dev.alejo.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteBookDao {
    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query("select * from BookEntity")
    fun getFavouriteBooks(): Flow<List<BookEntity>>

    @Query("select * from BookEntity where id = :id")
    suspend fun getFavouriteBookById(id: String): BookEntity?

    @Query("delete from BookEntity where id = :id")
    suspend fun deleteFavouriteBook(id: String)
}