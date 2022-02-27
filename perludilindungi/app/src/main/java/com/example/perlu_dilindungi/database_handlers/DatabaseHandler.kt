package com.example.perlu_dilindungi.database_handlers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.database.getIntOrNull
import com.example.perlu_dilindungi.models.BookmarkModel
import com.example.perlu_dilindungi.models.FaskesModel
import kotlinx.coroutines.currentCoroutineContext

val DB_NAME = "mydb"
val TABLE_NAME = "bookmarks"
val COL_FASKES_ID = "faskesId"
val COL_PROVINCE = "province"
val COL_CITY = "city"

class DatabaseHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_FASKES_ID + " INTEGER PRIMARY KEY," +
                COL_PROVINCE + " VARCHAR(256)," +
                COL_CITY + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertFaskesBookmark(faskes: FaskesModel) : Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_FASKES_ID, faskes.id)
        cv.put(COL_PROVINCE, faskes.provinsi)
        cv.put(COL_CITY, faskes.kota)
        val result = db.insert(TABLE_NAME, null, cv)
        return result != (-1).toLong()
    }

    fun deleteFaskesBookmark (faskesId : Int) : Boolean{
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$COL_FASKES_ID=$faskesId", null)
        db.close()

        return success != -1
    }

    @SuppressLint("Range")
    fun getFaskesBookmarkByFaskesId(faskesId : Int) : BookmarkModel? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "select * from $TABLE_NAME where $COL_FASKES_ID='$faskesId'",
            null
        );

        val bookmarks: ArrayList<BookmarkModel> = ArrayList()
        if(cursor.moveToFirst()){
            do{
                val faskesId = cursor.getInt(cursor.getColumnIndex(COL_FASKES_ID))
                val province = cursor.getString(cursor.getColumnIndex(COL_PROVINCE))
                val city = cursor.getString(cursor.getColumnIndex(COL_CITY))

                val b = BookmarkModel(faskesId, province, city)
                bookmarks.add(b)
            } while (cursor.moveToNext())
        }

        return if (bookmarks.size != 1){
            null
        } else {
            bookmarks[0]
        }
    }
}