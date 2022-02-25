package com.example.perlu_dilindungi.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.get
import com.example.perlu_dilindungi.R
import com.example.perlu_dilindungi.models.FaskesDetailModel
import com.example.perlu_dilindungi.models.FaskesModel

class FaskesListAdapter(private val context: Activity, private val arrayList: ArrayList<FaskesModel>)
    : ArrayAdapter<FaskesModel>(context, R.layout.faskes_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.faskes_item, null)

        val faskesNameText: TextView = view.findViewById(R.id.faskesNameText)
        val faskesType : TextView = view.findViewById(R.id.faskesType)
        val faskesAddress: EditText = view.findViewById(R.id.faskesAddressText)
        val faskesNumber: TextView = view.findViewById(R.id.faskesNumberText)
        val faskesCode: TextView = view.findViewById(R.id.faskesCodeText)

        val faskes :FaskesModel = arrayList[position]
        faskesNameText.text = faskes.nama
        faskesType.text = faskes.jenis_faskes
        faskesAddress.setText(faskes.alamat)
        faskesNumber.text = faskes.telp
        faskesCode.text = faskes.kode

        return view
    }
}