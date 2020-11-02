package com.example.ithunammaveedu.fragments.homefrag

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.parcel.Parcelize

class FragViewModel:ViewModel(){

  private var _foodList=MutableLiveData<ArrayList<Food>>()
    val  foodList:LiveData<ArrayList<Food>>
       get() = _foodList
   private val _foodCart=MutableLiveData<ArrayList<FoodOrderData>>()
    val foodCart:LiveData<ArrayList<FoodOrderData>>
       get() = _foodCart

    private var _total_price= MutableLiveData<Int>()
    val total_price: LiveData<Int>
            get()=_total_price

    private  var _enableButton=MutableLiveData<Boolean>()
    val enableButton:LiveData<Boolean>
        get() = _enableButton


    var foodOrderList= ArrayList<FoodOrderData>()//got in cart fragment

    init{
        _enableButton.value=false
        val database = FirebaseDatabase.getInstance().reference.child("Food")
        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
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
        }
        else{
            val orderItem=FoodOrderData(name = food.foodName,quantity=1,Total =(food.cost) )
            foodOrderList.add(orderItem)
        }
        setCartTotal()
        isEmpty()

    }
    fun subAnItemToList(food: Food){
        val getItem= foodOrderList.find { it.name==food.foodName }
        val setItem=foodList.value!!.find { it.foodName==food.foodName }
        if(setItem!!.initial==0){
            setItem.initial=0}
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
        setCartTotal()
        isEmpty()

    }
    fun setCartItems(){
        _foodCart.value=foodOrderList
    }
    fun removeAnCartitem(food:FoodOrderData){
        val k= foodList.value!!.find { it.foodName==food.name }
        k!!.initial=0;
        foodOrderList.remove(foodOrderList.find{it.name==food.name})
        _foodCart.value=foodOrderList
        setCartTotal()
        isEmpty()


    }
    fun increamentCartItem(food:FoodOrderData){
        val k= foodList.value!!.find { it.foodName==food.name }
        addAnItemToList(k!!)
        _foodCart.value=foodOrderList
        setCartTotal()
        isEmpty()


    }
    fun decreamentCartItem(food:FoodOrderData){
        val k=foodList.value!!.find { it.foodName==food.name }
        subAnItemToList(k!!)
        _foodCart.value=foodOrderList
        setCartTotal()
        isEmpty()



    }

    fun setCartTotal(){
        var tot:Int=0
        if(foodOrderList.isEmpty()){
            tot=0
        }
        else{
        foodOrderList.forEach {
           tot+= it.Total
        }
        }
        _total_price.value=tot
    }



    private fun isEmpty(){
        _enableButton.value = !foodOrderList.isEmpty()
    }


}
@Parcelize
data class FoodOrderData(var name:String="", var quantity:Int=0, var Total:Int=0): Parcelable