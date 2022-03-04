package com.example.perlu_dilindungi.request_controllers

import com.example.perlu_dilindungi.models.NewsModel
import com.example.perlu_dilindungi.models.NewsResponseModel
import com.example.perlu_dilindungi.services.IRetrofit
import com.example.perlu_dilindungi.view_models.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsController(newsViewModel: NewsViewModel) : Callback<NewsResponseModel?> {
    private var viewModel: NewsViewModel = newsViewModel

    fun start() {
        viewModel.newsFetching.value = true
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IRetrofit.BASE_URL_NEWS)
            .build()
        val service: IRetrofit = retrofit.create(IRetrofit::class.java)
        service.getNews()?.enqueue(this)
    }

    override fun onResponse(
        call: Call<NewsResponseModel?>,
        response: Response<NewsResponseModel?>
    ) {
        if(response.isSuccessful) {
            val newses = response.body()
            if (newses != null) {
                viewModel.news.value = newses.results
            }
        }
        viewModel.newsFetching.value = false
    }

    override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {
        viewModel.newsFetching.value = false
        TODO("Not yet implemented")
    }


}