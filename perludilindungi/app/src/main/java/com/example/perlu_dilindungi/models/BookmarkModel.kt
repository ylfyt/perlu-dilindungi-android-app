package com.example.perlu_dilindungi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkModel(

    @PrimaryKey
    val faskesId: Int = 0,
    val province: String,
    val city: String
)