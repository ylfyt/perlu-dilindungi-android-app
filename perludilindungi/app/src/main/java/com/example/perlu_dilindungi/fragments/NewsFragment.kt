package com.example.perlu_dilindungi.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.adapters.FaskesListAdapter
import com.example.perlu_dilindungi.adapters.NewsListAdapter
import com.example.perlu_dilindungi.request_controllers.NewsController
import com.example.perlu_dilindungi.view_models.NewsViewModel

class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel: NewsViewModel

    private val model: NewsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        NewsController(model).start()
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        model.news.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.i("Test", "NewsListFragment is not null")
                val newsListAdapter = NewsListAdapter(context as Activity, it)
                val newsListView: RecyclerView = view.findViewById(R.id.newsList)

                newsListView.adapter = newsListAdapter
                newsListView.setHasFixedSize(true)
                
            } else {
                Log.i("Test", "NewsListFragment is null")
                val newsListAdapter = NewsListAdapter(context as Activity, ArrayList())
                val newsListView : RecyclerView = view.findViewById(R.id.newsList)

                newsListView.adapter = newsListAdapter
                newsListView.setHasFixedSize(true)
            }
        })
    }
}