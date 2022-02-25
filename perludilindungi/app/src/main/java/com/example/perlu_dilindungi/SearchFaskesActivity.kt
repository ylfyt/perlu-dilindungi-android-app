package com.example.perlu_dilindungi

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perlu_dilindungi.fragments.FaskesListFragment
import com.example.perlu_dilindungi.request_controllers.CityController
import com.example.perlu_dilindungi.request_controllers.FaskesController
import com.example.perlu_dilindungi.request_controllers.ProvincesController
import com.example.perlu_dilindungi.view_models.FaskesViewModel


class SearchFaskesActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var faskesViewModel: FaskesViewModel
    private var initProvSpinner = true
    private var initCitySpinner = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_faskes)

        faskesViewModel = ViewModelProvider(this)[FaskesViewModel::class.java]

        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener{
            if (faskesViewModel.provinceSelected.value != null && faskesViewModel.citySelected.value != null){
                val city = faskesViewModel.cities.value?.get(faskesViewModel.citySelected.value!!)
                if (city != null) {
                    FaskesController(faskesViewModel).start(city.nama.toString())
                }
            }
        }

        faskesViewModel.provinceSelected.observe(this, Observer {
            if (it != null){
                val prov : ProvinceModel? = faskesViewModel.provinces.value?.get(it)
                if (prov != null){
                    CityController(faskesViewModel).start(prov.id.toString())
                }
            }
            else{
                faskesViewModel.cities.value = null
            }
        })

        faskesViewModel.citiesFetching.observe(this, Observer {
            val spinnerLoadingText : TextView = findViewById(R.id.citySpinnerLoadingText)
            if (it) {
                spinnerLoadingText.text = "Loading..."
            } else {
                spinnerLoadingText.text = ""
            }
        })

        faskesViewModel.provincesFetching.observe(this, Observer {
            val spinnerLoadingText : TextView = findViewById(R.id.provinceSpinnerLoadingText)
            if (it) {
                spinnerLoadingText.text = "Loading..."
            } else {
                spinnerLoadingText.text = ""
            }
        })

        faskesViewModel.provinces.observe(this, Observer {
            val provinceSpinner: Spinner = findViewById(R.id.province_spinner)
            val array: ArrayList<String> = ArrayList();
            if (it != null) {
                array.add("Pilih Provinsi")
                for (prov in it) {
                    array.add(prov.nama!!)
                }
            } else {
                Log.i("ViewModel:Provinces", "Null value")
            }

            val adapterTemp: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                array.toTypedArray()
            )
            adapterTemp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            provinceSpinner.adapter = adapterTemp
        })

        faskesViewModel.cities.observe(this, Observer {
            val citySpinner: Spinner = findViewById(R.id.city_spinner)
            val array: ArrayList<String> = ArrayList();
            if (it != null) {
                array.add("Pilih Kota")
                for (city in it) {
                    array.add(city.nama!!)
                }
            } else {
                Log.i("ViewModel:Cities", "Null value")
            }

            val adapterTemp: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                array.toTypedArray()
            )
            adapterTemp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            citySpinner.adapter = adapterTemp
        })

        faskesViewModel.faskesesFetching.observe(this, Observer {
            if (it){
                searchButton.text = "Please Wait..."
            }
            else{
                searchButton.text = "Search"
            }
        })

        faskesViewModel.faskeses.observe(this, Observer {
        })





        setOrientation()
        setupProvinceSpinner()
        setupCitySpinner()

        supportFragmentManager.beginTransaction()
            .replace(R.id.faskesListFragmentReplace, FaskesListFragment()).commit()
    }

    private fun setupCitySpinner() {
        val citySpinner: Spinner = findViewById(R.id.city_spinner)

        val testArray: Array<String> = arrayOf()

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        citySpinner.adapter = adapter
        citySpinner.onItemSelectedListener = this
    }

    private fun setupProvinceSpinner() {
        val provinceSpinner: Spinner = findViewById(R.id.province_spinner)

        val testArray: Array<String> = arrayOf()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        provinceSpinner.adapter = adapter
        provinceSpinner.onItemSelectedListener = this

        ProvincesController(faskesViewModel).start()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val spinnerId = parent.id;
        if (spinnerId == R.id.province_spinner) {
            if (initProvSpinner){
                initProvSpinner = false
//                if (faskesViewModel.provinceSelected.value != null){
//                    Log.i("Selection", parent[faskesViewModel.provinceSelected.value!!].toString())
//                    view?.findViewById<Spinner>(spinnerId)?.setSelection(faskesViewModel.provinceSelected.value!!)
//                }
                return
            }
            if (pos < 1){
                faskesViewModel.provinceSelected.value = null
            }
            else{
                faskesViewModel.provinceSelected.value = pos-1
            }
        } else if (spinnerId == R.id.city_spinner) {
            if (initCitySpinner){
                initCitySpinner = false
                return
            }
            if (pos < 1){
                faskesViewModel.citySelected.value = null
            }
            else{
                faskesViewModel.citySelected.value = pos-1
            }
        }

        faskesViewModel.faskeses.value = null
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.i("Spinner", "Nothing Selected")
    }

    private fun setOrientation() {
        val ml: LinearLayout = findViewById(R.id.mainLayout)
        val divider: View = findViewById(R.id.mainLayoutDivider)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ml.orientation = LinearLayout.HORIZONTAL

            divider.updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = 4
                height = ConstraintLayout.LayoutParams.MATCH_PARENT
                endToEnd = R.id.leftMainLayout
            }
        } else {
            ml.orientation = LinearLayout.VERTICAL
            divider.updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = ConstraintLayout.LayoutParams.MATCH_PARENT
                height = 4
                bottomToBottom = R.id.leftMainLayout
            }
        }
    }
}