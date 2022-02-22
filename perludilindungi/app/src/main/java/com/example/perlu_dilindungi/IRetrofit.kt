package com.example.perlu_dilindungi

import retrofit2.Call
import retrofit2.http.GET

interface IRetrofit {

    @get: GET("provinsi")
    val provinces : Call<ListOfProvince?>?

    companion object{
        const val BASE_URL = "https://dev.farizdotid.com/api/daerahindonesia/"
    }
}