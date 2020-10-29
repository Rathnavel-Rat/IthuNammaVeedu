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
    var foodOrderList= ArrayList<food_order>()
    fun fetchContacts() {
        val database = FirebaseDatabase.getInstance().reference.child("Doasi")
        database.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchlist=ArrayList<Food>()
                for(data in snapshot.children){
                   val c=data.getValue(Food::class.java)
                   fetchlist.add(c!!)
                  }
                _foodList.value=fetchlist
            }
        })
    }


    fun addAnItemToList(food: Food){
        val getItem= foodOrderList.find { it.name==food.foodName }
        val setItem=foodList.value!!.find { it.foodName==food.foodName }
        setItem!!.initial+=1
        _foodList.value=foodList.value
        if(getItem!=null){
            getItem.quantity+=1
            getItem.Total=getItem.quantity*food.cost
            println("jio ${foodOrderList}List")
        }
        else{
            val orderItem=food_order(name = food.foodName,quantity=1,Total =(food.cost) )
            foodOrderList.add(orderItem)
        }


    }
    fun subAnItemToList(food: Food){
        val getItem= foodOrderList.find { it.name==food.foodName }
        val setItem=foodList.value!!.find { it.foodName==food.foodName }
        if(setItem!!.initial==0){
            setItem.initial-=0}
        else{
            setItem.initial-=1
        }
        _foodList.value=foodList.value
        if(getItem!=null && getItem.quantity==1){
            foodOrderList.remove(foodOrderList.find{it.name==food.foodName})
        }
        else if(getItem!=null){
            getItem.quantity-=1;
            getItem.Total=getItem.quantity*food.cost
        }
    }
}

data class food_order(
    var name:String="",
    var quantity:Int=0,
    var Total:Int=0
)