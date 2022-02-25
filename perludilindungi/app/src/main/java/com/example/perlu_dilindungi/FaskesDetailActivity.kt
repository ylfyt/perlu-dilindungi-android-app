package com.example.perlu_dilindungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FaskesDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faskes_detail)

        val faskesId: Int = intent.getIntExtra("faskesId", 0)

        val faskesIdTextView: TextView = findViewById(R.id.faskesIdTextView)
        faskesIdTextView.text = faskesId.toString()

    }
}