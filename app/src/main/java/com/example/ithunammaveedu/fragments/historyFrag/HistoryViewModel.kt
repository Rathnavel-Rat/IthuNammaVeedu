package com.example.ithunammaveedu.fragments.historyFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ithunammaveedu.fragments.cartFrag.PlaceOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryViewModel :ViewModel(){
    private var _orderDetails= MutableLiveData<ArrayList<HistoryItem>>()
    val orderDetails:LiveData<ArrayList<HistoryItem>>
        get() = _orderDetails



    init {
        var arrayList=ArrayList<HistoryItem>()
        val db=FirebaseDatabase.getInstance().reference.child("Orders").child(FirebaseAuth.getInstance().currentUser!!.uid)
        db.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.removeAll(arrayList.asIterable())
               for(data in snapshot.children ) {
                   val c = data.getValue(PlaceOrder::class.java)
                   val d =HistoryItem(snapId = data.key!!,dataItems = c!!)
                   arrayList.add(d)
                   _orderDetails.value=arrayList
               }
            }

        })
    }


}
data class HistoryItem(
    var snapId:String="",
    var dataItems:PlaceOrder
)

