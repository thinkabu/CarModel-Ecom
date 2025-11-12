package com.example.ltdd_dacs3.Util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.ltdd_dacs3.Activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as MainActivity).findViewById<BottomNavigationView>(
            com.example.ltdd_dacs3.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as MainActivity).findViewById<BottomNavigationView>(
            com.example.ltdd_dacs3.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}