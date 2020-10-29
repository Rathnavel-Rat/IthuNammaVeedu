package com.example.ithunammaveedu.fragments.homefrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragViewModel:ViewModel(){

    private var _foodList=MutableLiveData<ArrayList<Food>>()
    val  foodList:LiveData<ArrayList<Food>>
       get() = _foodList

    fun fetchContacts() {
        val database = FirebaseDatabase.getInstance().reference.child("Doasi")
        database.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchlist=ArrayList<Food>()
                for(data in snapshot.children){
                   val c=data.getValue(Food::class.java)
                   fetchlist.add(c!!)
                  }
                println("$fetchlist jiooil")
                _foodList.value=fetchlist
            }

        })

    }
}