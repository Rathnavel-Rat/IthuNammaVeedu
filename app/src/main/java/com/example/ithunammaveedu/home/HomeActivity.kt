package com.example.ithunammaveedu.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.ActivityHomeBinding
import com.example.ithunammaveedu.fragments.service.notification

class HomeActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    lateinit var navController: NavController
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_home)
        binding.lifecycleOwner=this
        navController=this.findNavController(R.id.myNavHostFragment)
        drawerLayout=binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("INV.PrefrenceFile", Context.MODE_PRIVATE)
        val k=sharedPreferences.getBoolean("firstTime",false)
        if(!k){
            this.findNavController(R.id.myNavHostFragment).navigate(R.id.action_home2_to_yourInfo)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawerLayout)
    }


    override fun onStop() {
        startService(Intent(this,notification::class.java))
        super.onStop()
    }

    override fun onPause() {
        startService(Intent(this,notification::class.java))
        super.onPause()
    }

}
