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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perlu_dilindungi.FaskesDetailActivity
import com.example.perlu_dilindungi.MainActivity
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.adapters.FaskesListAdapter
import com.example.perlu_dilindungi.models.FaskesDetailModel
import com.example.perlu_dilindungi.models.FaskesModel
import com.example.perlu_dilindungi.view_models.FaskesViewModel


class FaskesListFragment : Fragment() {
    private lateinit var viewModel: FaskesViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faskes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        Log.i("Test", (2).toString())
        viewModel = ViewModelProvider(requireActivity())[FaskesViewModel::class.java]
        viewModel.faskeses.observe(requireActivity(), Observer{
            if (it != null){
                val faskesListAdapter = FaskesListAdapter(context as Activity, it)
                val faskesListView : ListView? = view?.findViewById(R.id.faskesListView)

                if (faskesListView != null) {
                    faskesListView.adapter = faskesListAdapter
                }

                faskesListView?.onItemClickListener =
                    OnItemClickListener { parent, view, position, id ->
                        val i = Intent(getContext(), FaskesDetailActivity::class.java)
                        i.putExtra("faskesId", it[position].id)
                        startActivity(i)
                    }
            }
            else{
                val faskesListAdapter = FaskesListAdapter(context as Activity, ArrayList())
                val faskesListView : ListView? = view?.findViewById(R.id.faskesListView)

                if (faskesListView != null) {
                    faskesListView.adapter = faskesListAdapter
                }
            }
        })
    }
}