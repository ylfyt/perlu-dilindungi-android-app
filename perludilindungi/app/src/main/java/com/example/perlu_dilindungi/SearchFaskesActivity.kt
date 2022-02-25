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
import com.example.perlu_dilindungi.models.FaskesModel
import com.example.perlu_dilindungi.request_controllers.CityController
import com.example.perlu_dilindungi.request_controllers.FaskesController
import com.example.perlu_dilindungi.request_controllers.ProvincesController
import com.example.perlu_dilindungi.view_models.FaskesViewModel


class SearchFaskesActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var faskesViewModel: FaskesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_faskes)

        faskesViewModel = ViewModelProvider(this)[FaskesViewModel::class.java]
//        Log.i("InitProv", (faskesViewModel.provinces.value == null).toString())
//        Log.i("InitCity", (faskesViewModel.cities.value == null).toString())
//        Log.i("InitFaskes", (faskesViewModel.faskeses.value == null).toString())

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

        faskesViewModel.faskeses.observe(this, Observer {
        })

        supportFragmentManager.beginTransaction()
            .replace(R.id.faskesListFragmentReplace, FaskesListFragment()).commit()

        setOrientation()
        setupProvinceSpinner()
        setupCitySpinner()
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
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val spinnerId = parent.id;
        if (spinnerId == R.id.province_spinner) {
            val prov : ProvinceModel? = faskesViewModel.provinces.value?.get(pos)
            if (prov != null){
                CityController(faskesViewModel).start(prov.id.toString())
            }
        } else if (spinnerId == R.id.city_spinner) {
            val cit = parent.getItemAtPosition(pos)
            FaskesController(faskesViewModel).start(cit.toString())

        }

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