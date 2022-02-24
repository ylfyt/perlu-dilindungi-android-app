package com.example.perlu_dilindungi.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perlu_dilindungi.CityModel
import com.example.perlu_dilindungi.ProvinceModel

class FaskesViewModel : ViewModel() {

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
}