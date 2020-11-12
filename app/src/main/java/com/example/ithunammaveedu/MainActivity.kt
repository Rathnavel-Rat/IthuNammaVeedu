package com.example.ithunammaveedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.ithunammaveedu.Starter.LoginActivity
import com.example.ithunammaveedu.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()
        Handler(Looper.getMainLooper()).postDelayed({
           if(auth.currentUser!=null){
           val intent= Intent(this, HomeActivity::class.java)
           startActivity(intent)
           }
           else{
               val intent= Intent(this,LoginActivity::class.java)
               startActivity(intent)
           }
            finish()
         },1000)
    }
}
