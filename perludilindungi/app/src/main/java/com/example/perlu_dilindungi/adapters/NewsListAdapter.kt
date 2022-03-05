package com.example.perlu_dilindungi.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.perlu_dilindungi.NewsDetailActivity
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.models.NewsEnclosure
import com.example.perlu_dilindungi.models.NewsModel
import com.squareup.picasso.Picasso

class NewsListAdapter (private val context: Activity, private val datas: ArrayList<NewsModel>)
: RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>(){
    class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val newsImageView : ImageView = view.findViewById(R.id.newsImage)
        val newsNameView : TextView = view.findViewById(R.id.newsName)
        val newsDateView : TextView = view.findViewById(R.id.newsDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        val holder = NewsViewHolder(adapterLayout)

        return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsModel = datas[position]
        Picasso.get().load(news.enclosure?._url).resize(93,64).centerCrop().into(holder.newsImageView)
        holder.newsNameView.setText(news.title)
        holder.newsDateView.setText(news.pubDate)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val i = Intent(context, NewsDetailActivity::class.java)
            i.putExtra("guid", news.guid)
            context.startActivity(i)
        }


    }

    override fun getItemCount() = datas.size
}