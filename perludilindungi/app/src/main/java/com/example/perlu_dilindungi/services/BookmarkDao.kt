package com.example.perlu_dilindungi.services

import androidx.room.*
import com.example.perlu_dilindungi.models.BookmarkModel

@Dao
interface BookmarkDao {
    @Insert
    suspend fun addBookmark(bookmark: BookmarkModel) : Long

    @Query("SELECT * FROM BookmarkModel")
    suspend fun getBookmarks() : List<BookmarkModel>

    @Query("SELECT * FROM BOOKMARKMODEL WHERE faskesId=:faskesId")
    suspend fun getBookmark(faskesId: Int) : List<BookmarkModel>

    @Update
    suspend fun updateBookmark(bookmark: BookmarkModel)

    @Query("DELETE FROM BookmarkModel WHERE faskesId=:faskesId")
    suspend fun deleteBookmark(faskesId: Int) : Int
}