package com.example.perlu_dilindungi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchFaskes : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_faskes)

        setupProvinceSpinner()
        setupCitySpinner()

        val rf = Retrofit.Builder()
            .baseUrl(IRetrofit.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val API:IRetrofit = rf.create(IRetrofit::class.java)
        val call: Call<ListOfProvince?>? = API.provinces

        val provinceSpinnerLoadingText: TextView = findViewById(R.id.provinceSpinnerLoadingText)
        provinceSpinnerLoadingText.text = "Loading..."
        call?.enqueue(object :Callback<ListOfProvince?>{
            override fun onResponse(
                call: Call<ListOfProvince?>,
                response: Response<ListOfProvince?>
            ) {
                provinceSpinnerLoadingText.text = "Success"
                if (response.isSuccessful){
                    val provinces = response.body()
                    Log.i("success", "Yeeeay");
                    if (provinces != null) {
                        if (provinces.provinsi != null){
                            for (prov in provinces.provinsi!!){
                                Log.i("Test", prov.nama!!);
                            }
                        }
                    }
                }
                else{
                    Log.i("Something Wrong", response.raw().toString())
                    provinceSpinnerLoadingText.text = "Fail"
                }
            }

            override fun onFailure(call: Call<ListOfProvince?>, t: Throwable) {
                provinceSpinnerLoadingText.text = "Fail"
                t.printStackTrace()
                Log.i("error", t.message.toString())
            }
        })
    }

    private fun setupCitySpinner() {
        val citySpinner: Spinner = findViewById(R.id.city_spinner)

        val testArray: Array<String> = arrayOf()

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        citySpinner.adapter = adapter
        citySpinner.onItemSelectedListener = this

//        val citySpinnerLoadingText: TextView = findViewById(R.id.citySpinnerLoadingText)
//        if (testArray.isEmpty()){
//            citySpinnerLoadingText.visibility = View.VISIBLE;
//        }
//        else{
//            citySpinnerLoadingText.visibility = View.GONE;
//        }
    }

    private fun setupProvinceSpinner() {
        val provinceSpinner: Spinner = findViewById(R.id.province_spinner)

        val testArray: Array<String> = arrayOf()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        provinceSpinner.adapter = adapter
        provinceSpinner.onItemSelectedListener = this


//        val provinceSpinnerLoadingText: TextView = findViewById(R.id.provinceSpinnerLoadingText)
//        if (testArray.isEmpty()){
//            provinceSpinnerLoadingText.visibility = View.VISIBLE;
//        }
//        else{
//            provinceSpinnerLoadingText.visibility = View.GONE;
//        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        val sel: String = parent.getItemAtPosition(pos) as String

        val spinnerId = parent.id;
        if (spinnerId == R.id.province_spinner){
            Log.i("SpinnerProvince", "Province : $sel");
        }
        else if (spinnerId == R.id.city_spinner){
            Log.i("SpinnerCity", "City : $sel");
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.i("Spinner", "Nothing Selected")
    }
}