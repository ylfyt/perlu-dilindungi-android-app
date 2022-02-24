package com.example.perlu_dilindungi.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.models.FaskesDetailModel
import com.example.perlu_dilindungi.models.FaskesModel

class FaskesListAdapter(private val context: Activity, private val arrayList: ArrayList<FaskesModel>)
    : ArrayAdapter<FaskesModel>(context, R.layout.faskes_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.faskes_item, null)

        val faskesNameText: TextView = view.findViewById(R.id.faskesNameText)

        faskesNameText.text = "Test 1"

        return view
    }
}