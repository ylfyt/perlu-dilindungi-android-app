package com.example.perlu_dilindungi

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.perlu_dilindungi.models.BookmarkModel
import com.example.perlu_dilindungi.models.FaskesModel
import com.example.perlu_dilindungi.services.BookmarkDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FaskesDetailActivity : AppCompatActivity() {

    private val db by lazy { BookmarkDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faskes_detail)

        val fs = FaskesModel()
        fs.id = intent.getIntExtra("id", 0)
        fs.provinsi = intent.getStringExtra("provinsi")
        fs.kota = intent.getStringExtra("kota")
        fs.alamat = intent.getStringExtra("alamat")
        fs.telp = intent.getStringExtra("telp")
        fs.kode = intent.getStringExtra("kode")
        fs.nama = intent.getStringExtra("nama")
        fs.jenis_faskes = intent.getStringExtra("jenis")
        fs.status = intent.getStringExtra("status")
        fs.latitude = intent.getStringExtra("latitude")
        fs.longitude = intent.getStringExtra("longitude")

        CoroutineScope(Dispatchers.IO).launch {
            val bookmarks: List<BookmarkModel> = db.bookmarkDao().getBookmark(fs.id)

            withContext(Dispatchers.Main){
                if (bookmarks.isNotEmpty())
                    setupDetail(fs, bookmarks[0])
                else
                    setupDetail(fs, null)
            }
        }


        val googleMapsButton: Button = findViewById(R.id.detail_GoogleMapButton)
        googleMapsButton.setOnClickListener {
            val uri =
                "http://maps.google.com/maps?q=loc:" + fs.latitude + "," + fs.longitude
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)

        }

        val bookmarkButton: Button = findViewById(R.id.detail_BookmarkButton)
        bookmarkButton.setOnClickListener{
            if (bookmarkButton.text == resources.getString(R.string.bookmark_text)){
                CoroutineScope(Dispatchers.IO).launch {
                    val result = db.bookmarkDao().addBookmark(
                        BookmarkModel(
                            faskesId = fs.id,
                            province = fs.provinsi!!,
                            city = fs.kota!!
                        )
                    )
                    if (result.toInt() == fs.id){
                        withContext(Dispatchers.Main){
                            setUnbookmarkButton(true)
                        }
                    }
                }
            }
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    val result = db.bookmarkDao().deleteBookmark(fs.id)

                    if (result == 1) {
                        withContext(Dispatchers.Main) {
                            setUnbookmarkButton(false)
                        }
                    }
                }
            }
        }


    }

    private fun setupDetail(fs: FaskesModel, bookmark: BookmarkModel?) {
        val faskeNameTextView: TextView = findViewById(R.id.detail_FaskesNameText)
        val faskeKodeTextView: TextView = findViewById(R.id.detail_FaskesKodeText)
        val faskeTypeTextView: TextView = findViewById(R.id.detail_FaskesType)
        val faskeAddressText: EditText = findViewById(R.id.detail_FaskesAddressText)
        val faskeNumberTextView: TextView = findViewById(R.id.detail_FaskesNumberText)
        val faskesStatusLogoImage: ImageView = findViewById(R.id.detail_FaskeStatusLogo)
        val faskesStatusText: TextView = findViewById(R.id.detail_FaskesStatusText)

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

        if (bookmark != null){
            setUnbookmarkButton(true)
        }
        else{
            setUnbookmarkButton(false)
        }
    }

    private fun setUnbookmarkButton(yes: Boolean){
        if (yes){
            val bookmarkButton: Button = findViewById(R.id.detail_BookmarkButton)
            bookmarkButton.text = resources.getString(R.string.unbookmark_text)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bookmarkButton.background.setTint(Color.parseColor("#7879F1"))
            }
        }
        else{
            val bookmarkButton: Button = findViewById(R.id.detail_BookmarkButton)
            bookmarkButton.text = resources.getString(R.string.bookmark_text)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bookmarkButton.background.setTint(Color.parseColor("#EF5DA8"))
            }
        }
    }
}