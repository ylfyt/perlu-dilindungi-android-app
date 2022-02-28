package com.example.perlu_dilindungi.services

import androidx.room.*
import com.example.perlu_dilindungi.models.BookmarkModel

@Dao
interface BookmarkDao {
    @Insert
    suspend fun addBookmark(bookmark: BookmarkModel)

    @Query("SELECT * FROM BookmarkModel")
    suspend fun getBookmarks() : List<BookmarkModel>

    suspend fun getBookmark(faskesId: Int) : List<BookmarkModel>

    @Update
    suspend fun updateBookmark(bookmark: BookmarkModel)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkModel)
}