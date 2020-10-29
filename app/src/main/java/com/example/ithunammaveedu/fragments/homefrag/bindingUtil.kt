package com.example.ithunammaveedu.fragments.homefrag

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("cost")
fun TextView.setCost(food: Food){
    food.let { text=food.cost.toString() }
}
@BindingAdapter("initial")
fun TextView.setInitial(food: Food){
    food.let { text=food.initial.toString() }
}
