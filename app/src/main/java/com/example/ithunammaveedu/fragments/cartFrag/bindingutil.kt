package com.example.ithunammaveedu.fragments.cartFrag

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData

@BindingAdapter("quantity")
fun TextView.setQuantity(item:FoodOrderData){
    item.apply { text=resources.getString(R.string.rupees)+item.quantity.toString() }

}
@BindingAdapter("total")
fun TextView.setTotal(item:FoodOrderData){
    item.apply { text=item.Total.toString() }

}