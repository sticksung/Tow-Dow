package com.example.towdow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.NavigationUI


import androidx.navigation.Navigation

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment


val bottomNavigationView: BottomNavigationView? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager: FragmentManager = supportFragmentManager

//        // define your fragments here
//        val fragment1: Fragment = HomeFragment()
//        val fragment2: Fragment = SearchFragment()
//        val fragment3: Fragment = ProfileFragment()
//
//      //  val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
//
//        // handle navigation selection
//        bottomNavigationView.setOnItemSelectedListener { item ->
//            lateinit var fragment: Fragment
//            when (item.itemId) {
//                R.id.home -> fragment = fragment1
//                R.id.Search -> fragment = fragment2
//                R.id.Profile -> fragment = fragment3
//            }
////          //  val navController = navHostFragment.navController
//            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(fragment.tag).commit()
//          //  navController.navigate(R.id.action_homeFragment_to_searchFragment)
//            true
//        }

        // Set default selection
     //   bottomNavigationView.selectedItemId = R.id.Home

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
}