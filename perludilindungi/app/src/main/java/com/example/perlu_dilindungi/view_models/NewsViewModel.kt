package com.example.perlu_dilindungi.view_models

import androidx.lifecycle.MutableLiveData
import com.example.perlu_dilindungi.models.NewsModel

class NewsViewModel {
    val newsFetching: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val news: MutableLiveData<ArrayList<NewsModel>?> by lazy {
        MutableLiveData<ArrayList<NewsModel>?>()
    }
}