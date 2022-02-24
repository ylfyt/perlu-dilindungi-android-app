package com.example.perlu_dilindungi.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.perlu_dilindungi.adapters.FaskesListAdapter
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.models.FaskesDetailModel
import com.example.perlu_dilindungi.models.FaskesModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FaskesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FaskesListFragment (
    private var faskesList: ArrayList<FaskesModel>
) : Fragment() {

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
        val faskesListAdapter = FaskesListAdapter(context as Activity, faskesList)
        val faskesListView : ListView = view.findViewById(R.id.faskesListView)

        faskesListView.adapter = faskesListAdapter
    }
}