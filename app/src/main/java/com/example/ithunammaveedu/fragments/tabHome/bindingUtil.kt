package com.example.ithunammaveedu.fragments.tabHome

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ithunammaveedu.R



@BindingAdapter("cost")
fun TextView.setCost(food: Food){
    food.let { text=resources.getString(R.string.rupees) +food.cost.toString() }
}
@BindingAdapter("initial")
fun TextView.setInitial(food: Food){
    food.let { text= food.initial.toString() }
}


@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String) {
    val image="https://drive.google.com/uc?export=download&id="+url.split("/")[5]
    Glide.with(view).load(image).apply( RequestOptions().override(600, 200)).into(view)


}