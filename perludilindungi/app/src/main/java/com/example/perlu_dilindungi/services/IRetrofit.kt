package com.example.perlu_dilindungi.services

import com.example.perlu_dilindungi.ListOfCity
import com.example.perlu_dilindungi.ListOfProvince
import com.example.perlu_dilindungi.models.FaskesResponseModel
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

    @GET("get-faskes-vaksinasi")
    fun getFaskeses(@Query("city") city: String) : Call<FaskesResponseModel>?

    companion object{
        const val BASE_URL_DAERAH = "https://dev.farizdotid.com/api/daerahindonesia/"
        const val BASE_URL_FASKES = "https://kipi.covid19.go.id/api/"
    }
}