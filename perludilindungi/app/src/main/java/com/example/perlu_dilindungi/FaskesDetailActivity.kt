package com.example.perlu_dilindungi

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perlu_dilindungi.request_controllers.FindOneFaskesController
import com.example.perlu_dilindungi.view_models.FaskesViewModel


class FaskesDetailActivity : AppCompatActivity() {

    private lateinit var faskesViewModel: FaskesViewModel
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faskes_detail)

        faskesViewModel = ViewModelProvider(this)[FaskesViewModel::class.java]
        faskesViewModel.faskes.value = null

        val faskesId: Int = intent.getIntExtra("faskesId", 0)
        val province: String? = intent.getStringExtra("province")
        val city: String? = intent.getStringExtra("city")

        faskesViewModel.faskes.observe(this, Observer {
            if (it != null) {
                loadingDialog.dismissLoading()
                setupDetail()
            } else {
                FindOneFaskesController(faskesViewModel).start(province!!, city!!, faskesId)
                loadingDialog = LoadingDialog(this)
                loadingDialog.startLoading()
            }
        })

        val googleMapsButton: Button = findViewById(R.id.detail_GoogleMapButton)
        googleMapsButton.setOnClickListener {
            val fs = faskesViewModel.faskes.value
            if (fs != null) {
                val uri =
                    "http://maps.google.com/maps?q=loc:" + fs.latitude + "," + fs.longitude
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }

        }
    }

    private fun setupDetail() {
        val faskeNameTextView: TextView = findViewById(R.id.detail_FaskesNameText)
        val faskeKodeTextView: TextView = findViewById(R.id.detail_FaskesKodeText)
        val faskeTypeTextView: TextView = findViewById(R.id.detail_FaskesType)
        val faskeAddressText: EditText = findViewById(R.id.detail_FaskesAddressText)
        val faskeNumberTextView: TextView = findViewById(R.id.detail_FaskesNumberText)
        val faskesStatusLogoImage: ImageView = findViewById(R.id.detail_FaskeStatusLogo)
        val faskesStatusText: TextView = findViewById(R.id.detail_FaskesStatusText)

        val fs = faskesViewModel.faskes.value
        if (fs != null) {
            faskeNameTextView.text = fs.nama
            faskeKodeTextView.text = "Kode: " + fs.kode
            if (fs.jenis_faskes != "") {
                faskeTypeTextView.text = fs.jenis_faskes
                if (fs.jenis_faskes == "RUMAH SAKIT") {
                    faskeTypeTextView.setBackgroundColor(Color.parseColor("#5D5FEF"))
                }
                if (fs.jenis_faskes == "KLINIK") {
                    faskeTypeTextView.setBackgroundColor(Color.parseColor("#7879F1"))
                }
            } else {
                faskeTypeTextView.visibility = View.INVISIBLE
            }
            faskeAddressText.setText(fs.alamat)
            if (fs.telp != null)
                faskeNumberTextView.text = "No telp: " + fs.telp
            else
                faskeNumberTextView.text = "No telp: "
            faskesStatusText.text = fs.status
            if (fs.status == "Siap Vaksinasi") {
                faskesStatusLogoImage.setImageResource(R.drawable.siap_logo)
            } else {
                faskesStatusLogoImage.setImageResource(R.drawable.tidak_siap_logo)
            }
        }
    }
}