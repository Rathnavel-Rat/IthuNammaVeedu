package com.example.ithunammaveedu.fragments.tabHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.FoodItemViewBinding


class Adapter(var list:List<Food>, private val itemAddClickListener: AddClickListener, private val itemSubClickListener: SubClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    fun setData(items:List<Food>){
        list=items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodItemViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(
            binding
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.bind(list[position],itemAddClickListener,itemSubClickListener)
        }

    class ViewHolder(val binding: FoodItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, itemAddClickListener: AddClickListener, itemSubClickListener: SubClickListener) {
            binding.food = food
            binding.addclicklistener=itemAddClickListener
            binding.subclicklistener=itemSubClickListener
            binding.executePendingBindings()
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}

class AddClickListener(val clickListener: (food: Food) -> Unit) {
    fun onClick(item: Food)= clickListener(item)
}

class SubClickListener(val clickListener: (food: Food) -> Unit) {
    fun onClick(item: Food) = clickListener(item)
}



