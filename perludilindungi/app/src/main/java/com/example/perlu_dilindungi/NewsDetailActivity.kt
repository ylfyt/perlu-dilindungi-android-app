package com.example.perlu_dilindungi

import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val newsURL: TextView = findViewById(R.id.newsURL)
        val newsDetail: WebView = findViewById(R.id.newsDetail)
        val url = intent.getStringExtra("guid")
        newsURL.setText(url)
        newsDetail.loadUrl(url!!)
    }
}