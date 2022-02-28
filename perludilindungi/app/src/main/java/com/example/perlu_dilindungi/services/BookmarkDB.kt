package com.example.perlu_dilindungi.services

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.perlu_dilindungi.models.BookmarkModel

@Database(
    entities = [BookmarkModel::class],
    version = 1
)
abstract class BookmarkDB : RoomDatabase(){

    abstract fun bookmarkDao() : BookmarkDao

    companion object {

        @Volatile private var instance : BookmarkDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookmarkDB::class.java,
            "bookmark.db"
        ).build()
    }
}