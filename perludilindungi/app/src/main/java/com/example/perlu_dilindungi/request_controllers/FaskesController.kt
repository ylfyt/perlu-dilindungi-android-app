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

class FaskesController(faskesViewModel: FaskesViewModel) : Callback<FaskesResponseModel?> {
    private var viewModel: FaskesViewModel = faskesViewModel

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0

    fun start(province: String, city: String, longitude: Double, latitude: Double) {
        currentLatitude = latitude
        currentLongitude = longitude
        viewModel.faskesesFetching.value = true
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
                val fas = getFiveFaskes(faskeses.data)
                viewModel.faskeses.value = fas
            }
        }
        viewModel.faskesesFetching.value = false
    }

    private fun getFiveFaskes(faskeses: ArrayList<FaskesModel>?) : ArrayList<FaskesModel>? {
        if (faskeses == null)
            return null
        val distances = FloatArray(faskeses.size)
        val indexes = IntArray(faskeses.size)
        faskeses.forEachIndexed() { idx, it ->
            val result = FloatArray(1)
            Location.distanceBetween(
                currentLatitude,
                currentLongitude,
                it.latitude!!.toDouble(),
                it.longitude!!.toDouble(),
                result,
            )
            distances[idx] = result[0]
            indexes[idx] = idx
        }
        for (i in 0 until faskeses.size-1){
            var minIdx = i
            for (j in i+1 until faskeses.size){
                if (distances[j] < distances[minIdx]){
                    minIdx = j
                }
            }
            val idxTemp = indexes[i]
            val disTemp = distances[i]

            indexes[i] = indexes[minIdx]
            distances[i] = distances[minIdx]

            indexes[minIdx] = idxTemp
            distances[minIdx] = disTemp
        }

        val count = min(5, faskeses.size)
        val result = ArrayList<FaskesModel>(count)
        for (i in 0 until count){
            result.add(faskeses[indexes[i]])
        }
        return  result
    }

    override fun onFailure(call: Call<FaskesResponseModel?>, t: Throwable) {
        viewModel.faskesesFetching.value = false
        TODO("Not yet implemented")
    }
}