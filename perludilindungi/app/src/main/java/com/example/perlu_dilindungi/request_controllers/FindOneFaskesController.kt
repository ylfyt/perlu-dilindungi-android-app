package com.example.perlu_dilindungi.request_controllers

import android.location.Location
import com.example.perlu_dilindungi.view_models.FaskesViewModel
import com.example.perlu_dilindungi.services.IRetrofit
import com.example.perlu_dilindungi.models.FaskesModel
import com.example.perlu_dilindungi.models.FaskesResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.min

class FindOneFaskesController(faskesViewModel: FaskesViewModel) : Callback<FaskesResponseModel?> {
    private var viewModel: FaskesViewModel = faskesViewModel

    private var faskesId: Int = 0

    fun start(province: String, city: String, faskesId: Int) {
        this.faskesId = faskesId
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IRetrofit.BASE_URL_FASKES)
            .build()
        val service: IRetrofit = retrofit.create(IRetrofit::class.java)
        service.getFaskeses(province, city)?.enqueue(this)
    }

    override fun onResponse(
        call: Call<FaskesResponseModel?>,
        response: Response<FaskesResponseModel?>
    ) {
        if (response.isSuccessful) {
            val faskeses = response.body()
            if (faskeses != null) {
                val fas = getOneFaskes(faskeses.data)
                viewModel.faskes.value = fas
            }
        }
    }

    private fun getOneFaskes(faskeses: ArrayList<FaskesModel>?) : FaskesModel? {
        if (faskeses == null)
            return null

        for (i in 0 until faskeses.size){
            if (faskeses[i].id == faskesId){
                return faskeses[i]
            }
        }

        return null
    }

    override fun onFailure(call: Call<FaskesResponseModel?>, t: Throwable) {
        viewModel.faskesesFetching.value = false
        TODO("Not yet implemented")
    }
}