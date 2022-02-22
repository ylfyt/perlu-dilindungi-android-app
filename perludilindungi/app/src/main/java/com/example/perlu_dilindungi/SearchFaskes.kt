package com.example.perlu_dilindungi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SearchFaskes : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_faskes)

        setupProvinceSpinner()
        setupCitySpinner()
    }

    private fun setupCitySpinner() {
        val citySpinner: Spinner = findViewById(R.id.city_spinner)

        val testArray: Array<String> = arrayOf("Kota Bandung", "Padang", "Payakumbuh")

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        citySpinner.adapter = adapter
        citySpinner.onItemSelectedListener = this

        val citySpinnerLoadingText: TextView = findViewById(R.id.citySpinnerLoadingText)
        if (testArray.isEmpty()){
            citySpinnerLoadingText.visibility = View.VISIBLE;
        }
        else{
            citySpinnerLoadingText.visibility = View.GONE;
        }
    }

    private fun setupProvinceSpinner() {
        val provinceSpinner: Spinner = findViewById(R.id.province_spinner)

        val testArray: Array<String> = arrayOf("Jawa Barat", "Sumatera Barat", "Jawa Timur")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        provinceSpinner.adapter = adapter
        provinceSpinner.onItemSelectedListener = this


        val provinceSpinnerLoadingText: TextView = findViewById(R.id.provinceSpinnerLoadingText)
        if (testArray.isEmpty()){
            provinceSpinnerLoadingText.visibility = View.VISIBLE;
        }
        else{
            provinceSpinnerLoadingText.visibility = View.GONE;
        }
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