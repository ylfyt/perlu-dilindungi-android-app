package com.example.perlu_dilindungi.services

import com.example.perlu_dilindungi.ListOfCity
import com.example.perlu_dilindungi.ListOfProvince
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @get: GET("provinsi")
    val provinces : Call<ListOfProvince?>?

    @GET("provinsi")
    fun getProvincies(): Call<ListOfProvince?>?

    @GET("kota")
    fun getCities(@Query("id_provinsi") idProvinsi: String) : Call<ListOfCity?>?

    companion object{
        const val BASE_URL = "https://dev.farizdotid.com/api/daerahindonesia/"
    }
}