package com.example.perlu_dilindungi

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(private var activity: Activity) {

    private lateinit var dialog: AlertDialog

    fun startLoading(){
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissLoading(){
        dialog.dismiss()
    }

}