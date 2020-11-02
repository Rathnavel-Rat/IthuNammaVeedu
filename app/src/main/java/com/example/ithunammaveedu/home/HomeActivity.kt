package com.example.ithunammaveedu.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    lateinit var navController: NavController
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_home)

        navController=this.findNavController(R.id.myNavHostFragment)
        drawerLayout=binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawerLayout)
    }



}
