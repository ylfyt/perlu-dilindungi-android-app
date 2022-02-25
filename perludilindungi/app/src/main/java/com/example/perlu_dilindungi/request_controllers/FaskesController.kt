package com.example.perlu_dilindungi.request_controllers

import android.location.LocationManager
import android.util.Log
import com.example.perlu_dilindungi.view_models.FaskesViewModel
import com.example.perlu_dilindungi.services.IRetrofit
import com.example.perlu_dilindungi.ListOfProvince
import com.example.perlu_dilindungi.models.FaskesResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FaskesController(faskesViewModel: FaskesViewModel) : Callback<FaskesResponseModel?> {
    private var viewModel: FaskesViewModel = faskesViewModel

    fun start(city: String) {
        viewModel.faskesesFetching.value = true
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IRetrofit.BASE_URL_FASKES)
            .build()
        val service: IRetrofit = retrofit.create(IRetrofit::class.java)
        val cit = city.replace("kabupaten", "KAB.", true)
        service.getFaskeses(cit)?.enqueue(this)
    }

    override fun onResponse(call: Call<FaskesResponseModel?>, response: Response<FaskesResponseModel?>) {
        if (response.isSuccessful){
            val faskeses = response.body()
            if (faskeses != null) {
                viewModel.faskeses.value = faskeses.data
            }
        }
        viewModel.faskesesFetching.value = false
    }

    override fun onFailure(call: Call<FaskesResponseModel?>, t: Throwable) {
        viewModel.faskesesFetching.value = false
        TODO("Not yet implemented")
    }
}