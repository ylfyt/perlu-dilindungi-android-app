package com.example.perlu_dilindungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.perlu_dilindungi.fragments.SearchFaskesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchFaskesActivity : Fragment = SearchFaskesFragment()
        replaceFragment(searchFaskesActivity)
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.appLayout, fragment)
        transaction.commit()
    }
}