package com.example.ithunammaveedu.fragments.tabHome

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Food(
    var id:Int=0,
    var foodName:String="",
    var cost:Int=0,
    var initial:Int=0,
    var image:String="",
    var category:String="",
    var type:String="",
    var available:Boolean=false

)