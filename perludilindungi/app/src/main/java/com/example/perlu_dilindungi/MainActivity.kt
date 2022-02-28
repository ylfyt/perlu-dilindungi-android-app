package com.example.perlu_dilindungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.perlu_dilindungi.fragments.NewsFragment
import com.example.perlu_dilindungi.fragments.SearchFaskesFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private val searchFaskesFragment : Fragment = SearchFaskesFragment()
    private val newsFragment : Fragment = NewsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(newsFragment)

        val navigationBar: NavigationBarView = findViewById(R.id.navbarView)
        navigationBar.setOnItemSelectedListener {
            if (it.itemId == R.id.nav_news){
                replaceFragment(newsFragment)
            }
            if (it.itemId == R.id.nav_location){
                replaceFragment(searchFaskesFragment)
            }
            if (it.itemId == R.id.nav_bookmark){
                Log.i("Navbar", "Bookmark")
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.appLayout, fragment)
        transaction.commit()
    }
}