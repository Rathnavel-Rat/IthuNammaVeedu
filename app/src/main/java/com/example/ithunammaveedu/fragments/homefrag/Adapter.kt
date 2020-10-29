package com.example.ithunammaveedu.fragments.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.FoodItemViewBinding


class Adapter(foodList: ArrayList<Food>, private val itemAddClickListener: AddClickListener, private val itemSubClickListener: SubClickListener ) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var list: ArrayList<Food> = foodList

    fun setData(items:ArrayList<Food>){
         list=items
        println("setData $list")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodItemViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food: Food = list[position]
        holder.bind(food,itemAddClickListener,itemSubClickListener)

    }

    class ViewHolder(val binding: FoodItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, itemAddClickListener: AddClickListener, itemSubClickListener: SubClickListener) {
            binding.food = food
            binding.addclicklistener=itemAddClickListener
            binding.subclicklistener=itemSubClickListener
            binding.executePendingBindings()
        }

    }
}

class AddClickListener(val clickListener: (food:Food) -> Unit) {
    fun onClick(item:Food)= clickListener(item)
}

class SubClickListener(val clickListener: (food: Food) -> Unit) {
    fun onClick(item:Food) = clickListener(item)
}

