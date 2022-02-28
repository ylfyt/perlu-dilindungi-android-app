package com.example.perlu_dilindungi.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.models.ProvinceModel
import com.example.perlu_dilindungi.request_controllers.CityController
import com.example.perlu_dilindungi.request_controllers.FaskesController
import com.example.perlu_dilindungi.request_controllers.ProvincesController
import com.example.perlu_dilindungi.view_models.FaskesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class SearchFaskesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var fusedLocationClient : FusedLocationProviderClient
//    private lateinit var faskesViewModel: FaskesViewModel
    private var initProvSpinner = true
    private var initCitySpinner = true
    private var permissionGranted = false

    private var longitude: Double? = null
    private var latitude: Double? = null

    private val faskesViewModel: FaskesViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_search_faskes, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissionGranted = when {
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) -> {
                    // Precise location access granted.
                    getLocation()
                    true
                }
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    // Only approximate location access granted.
                    getLocation()
                    true
                }
                else -> {
                    // No location access granted.
                    false
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

//        faskesViewModel = ViewModelProvider(this)[FaskesViewModel::class.java]

        val searchButton: Button = rootView.findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            if (faskesViewModel.provinceSelected.value != null && faskesViewModel.citySelected.value != null) {
                if (!permissionGranted) {
                    locationPermissionRequest.launch(
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )

                    return@setOnClickListener
                }

                if (latitude == null || longitude == null){
                    Toast.makeText(activity, "Location unknown", Toast.LENGTH_LONG).show()
                    getLocation()
                    return@setOnClickListener
                }

                val city = faskesViewModel.cities.value?.get(faskesViewModel.citySelected.value!!)
                val prov = faskesViewModel.provinces.value?.get(faskesViewModel.provinceSelected.value!!)
                if (city != null && prov != null) {
                    FaskesController(faskesViewModel).start(prov.key!!, city.key!!,
                        longitude!!, latitude!!)
                }
            }
        }

        faskesViewModel.provinceSelected.observe(requireActivity(), Observer {
            if (it != null) {
                val prov: ProvinceModel? = faskesViewModel.provinces.value?.get(it)
                if (prov != null) {
                    CityController(faskesViewModel).start(prov.key!!)
                }
            } else {
                faskesViewModel.cities.value = null
            }
        })

        faskesViewModel.citiesFetching.observe(requireActivity(), Observer {
            val spinnerLoadingText: TextView? = view?.findViewById(R.id.citySpinnerLoadingText)
            if (it) {
                spinnerLoadingText?.text = "Loading..."
            } else {
                spinnerLoadingText?.text = ""
            }
        })

        faskesViewModel.provincesFetching.observe(requireActivity(), Observer {
            val spinnerLoadingText: TextView = rootView.findViewById(R.id.provinceSpinnerLoadingText)
            if (it) {
                spinnerLoadingText.text = "Loading..."
            } else {
                spinnerLoadingText.text = ""
            }
        })

        activity?.let {
            faskesViewModel.provinces.observe(it, Observer {
                val provinceSpinner: Spinner = rootView.findViewById(R.id.province_spinner)
                val array: ArrayList<String> = ArrayList();
                if (it != null) {
                    array.add("Pilih Provinsi")
                    for (prov in it) {
                        array.add(prov.value!!)
                    }
                } else {
                    Log.i("ViewModel:Provinces", "Null value")
                }

                val adapterTemp: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireActivity(),
                    android.R.layout.simple_spinner_item,
                    array.toTypedArray()
                )
                adapterTemp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                provinceSpinner.adapter = adapterTemp
            })
        }

        faskesViewModel.cities.observe(requireActivity(), Observer {
            val citySpinner: Spinner = rootView.findViewById(R.id.city_spinner)
            val array: ArrayList<String> = ArrayList();
            if (it != null) {
                array.add("Pilih Kota")
                for (city in it) {
                    array.add(city.value!!)
                }
            } else {
                Log.i("ViewModel:Cities", "Null value")
            }

            val adapterTemp: ArrayAdapter<String> = ArrayAdapter<String>(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                array.toTypedArray()
            )
            adapterTemp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            citySpinner.adapter = adapterTemp
        })

        faskesViewModel.faskesesFetching.observe(requireActivity(), Observer {
            if (it) {
                searchButton.text = "Please Wait..."
            } else {
                searchButton.text = "Search"
            }
        })

        faskesViewModel.faskeses.observe(requireActivity(), Observer {
        })


        setOrientation(rootView)
        setupProvinceSpinner(rootView)
        setupCitySpinner(rootView)

        parentFragmentManager.beginTransaction()
            .replace(R.id.faskesListFragmentReplace, FaskesListFragment()).commit()

        return rootView
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let {
                longitude = it.longitude
                latitude = it.latitude
            }
        }
    }

    private fun setupCitySpinner(rootView: View) {
        val citySpinner: Spinner = rootView.findViewById(R.id.city_spinner)

        val testArray: Array<String> = arrayOf()

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        citySpinner.adapter = adapter
        citySpinner.onItemSelectedListener = this
    }

    private fun setupProvinceSpinner(rootView: View) {
        val provinceSpinner: Spinner = rootView.findViewById(R.id.province_spinner)

        val testArray: Array<String> = arrayOf()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, testArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        provinceSpinner.adapter = adapter
        provinceSpinner.onItemSelectedListener = this

        ProvincesController(faskesViewModel).start()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val spinnerId = parent.id;
        if (spinnerId == R.id.province_spinner) {
            if (initProvSpinner) {
                initProvSpinner = false
                return
            }
            if (pos < 1) {
                faskesViewModel.provinceSelected.value = null
            } else {
                faskesViewModel.provinceSelected.value = pos - 1
            }
        } else if (spinnerId == R.id.city_spinner) {
            if (initCitySpinner) {
                initCitySpinner = false
                return
            }
            if (pos < 1) {
                faskesViewModel.citySelected.value = null
            } else {
                faskesViewModel.citySelected.value = pos - 1
            }
        }

        faskesViewModel.faskeses.value = null
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.i("Spinner", "Nothing Selected")
    }

    private fun setOrientation(rootView: View) {
        val ml: LinearLayout = rootView.findViewById(R.id.mainLayout)
        val divider: View = rootView.findViewById(R.id.mainLayoutDivider)

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