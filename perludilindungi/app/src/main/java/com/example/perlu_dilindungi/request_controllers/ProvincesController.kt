package com.example.perlu_dilindungi.request_controllers

import com.example.perlu_dilindungi.view_models.FaskesViewModel
import com.example.perlu_dilindungi.services.IRetrofit
import com.example.perlu_dilindungi.ListOfProvince
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProvincesController(faskesViewModel: FaskesViewModel) : Callback<ListOfProvince?> {
    private var viewModel: FaskesViewModel = faskesViewModel

    fun start() {
        viewModel.provincesFetching.value = true
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IRetrofit.BASE_URL)
            .build()
        val service: IRetrofit = retrofit.create(IRetrofit::class.java)
        service.getProvincies()?.enqueue(this)
    }

    override fun onResponse(call: Call<ListOfProvince?>, response: Response<ListOfProvince?>) {
        if (response.isSuccessful){
            val provinces = response.body()
            if (provinces != null) {
                viewModel.provinces.value = provinces.provinsi
            }
        }
        viewModel.provincesFetching.value = false
    }

    override fun onFailure(call: Call<ListOfProvince?>, t: Throwable) {
        viewModel.provincesFetching.value = false
        TODO("Not yet implemented")
    }
}