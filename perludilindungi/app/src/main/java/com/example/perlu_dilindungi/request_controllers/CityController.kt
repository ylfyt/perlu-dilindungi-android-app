package com.example.perlu_dilindungi.request_controllers

import com.example.perlu_dilindungi.view_models.FaskesViewModel
import com.example.perlu_dilindungi.services.IRetrofit
import com.example.perlu_dilindungi.ListOfCity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityController(faskesViewModel: FaskesViewModel) : Callback<ListOfCity?> {
    private var viewModel: FaskesViewModel = faskesViewModel

    fun start(provId: String) {
        viewModel.citiesFetching.value = true
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IRetrofit.BASE_URL)
            .build()
        val service: IRetrofit = retrofit.create(IRetrofit::class.java)
        service.getCities(provId)?.enqueue(this)
    }

    override fun onResponse(call: Call<ListOfCity?>, response: Response<ListOfCity?>) {
        if (response.isSuccessful){
            val cities = response.body()
            if (cities != null) {
                viewModel.cities.value = cities.kota_kabupaten
            }
        }

        viewModel.citiesFetching.value = false
    }

    override fun onFailure(call: Call<ListOfCity?>, t: Throwable) {
        viewModel.citiesFetching.value = false
        TODO("Not yet implemented")
    }


}