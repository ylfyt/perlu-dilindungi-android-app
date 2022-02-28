package com.example.perlu_dilindungi.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perlu_dilindungi.CityModel
import com.example.perlu_dilindungi.models.BookmarkModel
import com.example.perlu_dilindungi.models.ProvinceModel
import com.example.perlu_dilindungi.models.FaskesModel

class FaskesViewModel : ViewModel() {

    val provinceSelected: MutableLiveData<Int?> by lazy {
        MutableLiveData<Int?>()
    }
    val citySelected: MutableLiveData<Int?> by lazy {
        MutableLiveData<Int?>()
    }

    val provincesFetching: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val provinces: MutableLiveData<ArrayList<ProvinceModel>?> by lazy {
        MutableLiveData<ArrayList<ProvinceModel>?>()
    }

    val citiesFetching: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val cities: MutableLiveData<ArrayList<CityModel>?> by lazy {
        MutableLiveData<ArrayList<CityModel>?>()
    }

    val faskesesFetching: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val faskeses : MutableLiveData<ArrayList<FaskesModel>?> by lazy {
        MutableLiveData<ArrayList<FaskesModel>?>()
    }
}