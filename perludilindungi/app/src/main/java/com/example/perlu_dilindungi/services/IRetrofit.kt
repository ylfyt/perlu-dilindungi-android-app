package com.example.perlu_dilindungi.services

import com.example.perlu_dilindungi.ListOfCity
import com.example.perlu_dilindungi.models.ListOfProvince
import com.example.perlu_dilindungi.models.FaskesResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @GET("get-province")
    fun getProvincies(): Call<ListOfProvince?>?

    @GET("get-city")
    fun getCities(@Query("start_id") start_id: String) : Call<ListOfCity?>?

    @GET("get-faskes-vaksinasi")
    fun getFaskeses(@Query("province") province: String, @Query("city") city: String) : Call<FaskesResponseModel>?

    companion object{
        const val BASE_URL_DAERAH = "https://perludilindungi.herokuapp.com/api/"
        const val BASE_URL_FASKES = "https://perludilindungi.herokuapp.com/api/"
    }
}