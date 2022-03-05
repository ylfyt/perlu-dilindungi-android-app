package com.example.perlu_dilindungi.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perlu_dilindungi.FaskesDetailActivity
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.adapters.FaskesListAdapter
import com.example.perlu_dilindungi.models.BookmarkModel
import com.example.perlu_dilindungi.request_controllers.FindOneFaskesController
import com.example.perlu_dilindungi.services.BookmarkDB
import com.example.perlu_dilindungi.view_models.FaskesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookMarkFaskesFragment : Fragment() {
    private val db by lazy { BookmarkDB(requireActivity()) }
    private val model: FaskesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.i("Test", "FaskesListFragment0")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.i("Test", "FaskesListFragment1")
        return inflater.inflate(R.layout.fragment_faskes_bookmark, container, false)
    }
    private suspend fun fetchAllFaskes(bookmarks : List<BookmarkModel>){
        bookmarks.forEach {
            FindOneFaskesController(model).start(it.province,it.city,it.faskesId)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Log.i("Test", "FaskesListFragment2")
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarks: List<BookmarkModel> = db.bookmarkDao().getBookmarks()
            fetchAllFaskes(bookmarks)
        }

//        viewModel = ViewModelProvider(requireActivity())[FaskesViewModel::class.java]
        model.bookmarkfaskes.observe(viewLifecycleOwner, Observer{
            if (it != null){
                Log.i("Test", "FaskesListFragment Faskes is not null")
                val faskesListAdapter = FaskesListAdapter(context as Activity, it)
                val faskesListView : ListView = view.findViewById(R.id.faskesListView)

                faskesListView.adapter = faskesListAdapter

                faskesListView.onItemClickListener =
                    OnItemClickListener { _, _, position, _ ->
                        val i = Intent(context, FaskesDetailActivity::class.java)
                        i.putExtra("id", it[position].id)
                        i.putExtra("provinsi", it[position].provinsi)
                        i.putExtra("kota", it[position].kota)
                        i.putExtra("alamat", it[position].alamat)
                        i.putExtra("kode", it[position].kode)
                        i.putExtra("telp", it[position].telp)
                        i.putExtra("status", it[position].status)
                        i.putExtra("jenis", it[position].jenis_faskes)
                        i.putExtra("nama", it[position].nama)
                        i.putExtra("latitude", it[position].latitude)
                        i.putExtra("longitude", it[position].longitude)
                        startActivity(i)
                    }
            }
            else{
                Log.i("Test", "FaskesListFragment Faskes is null")
                val faskesListAdapter = FaskesListAdapter(context as Activity, ArrayList())
                val faskesListView : ListView = view.findViewById(R.id.faskesListView)
                faskesListView.adapter = faskesListAdapter
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        Log.i("Test", "FaskesListFragment3")
    }
}