package com.example.ithunammaveedu.fragments.cartFrag

import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData
import java.util.*
import kotlin.collections.ArrayList

data class PlaceOrder(
    var date:String="",
    var total:String="",
    var address:String="",
    var phoneNumber:String="",
    var status:String="",
    var foodItem:ArrayList<FoodOrderData> = arrayListOf(),
    var name:String=""
)

