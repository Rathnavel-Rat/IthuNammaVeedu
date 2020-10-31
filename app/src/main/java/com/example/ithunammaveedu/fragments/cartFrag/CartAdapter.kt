package com.example.ithunammaveedu.fragments.cartFrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.CartItemViewBinding
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData

class CartAdapter(var foodList: ArrayList<FoodOrderData>, val itemAddClickListener: AddClickListener, val itemSubClickListener: SubClickListener, val removeClickListener: RemoveClickListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var list: ArrayList<FoodOrderData> = foodList
    fun setDaa(lists:ArrayList<FoodOrderData>){
        list=lists
        println("poojasarswathi $list")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CartItemViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food: FoodOrderData = list[position]
        holder.bind(food,itemAddClickListener,itemSubClickListener,removeClickListener)

    }

    class ViewHolder(val binding:CartItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: FoodOrderData, itemAddClickListener: AddClickListener, itemSubClickListener: SubClickListener, removeClickListener: RemoveClickListener) {
            binding.cart = cart
            binding.addclicklistener=itemAddClickListener
            binding.subclicklistener=itemSubClickListener
            binding.removeclicklistener=removeClickListener
            binding.executePendingBindings()
        }
    }
}

class AddClickListener(val clickListener: (cart_item:FoodOrderData) -> Unit) {
    fun onClick(cart_item: FoodOrderData) = clickListener(cart_item)

}

class SubClickListener(val clickListener: (cart_item:FoodOrderData) -> Unit) {
    fun onClick(cart_item:FoodOrderData) = clickListener(cart_item)
}

class RemoveClickListener(val clickListener: (cart_item:FoodOrderData) -> Unit) {
    fun onClick(cart_item:FoodOrderData) = clickListener(cart_item)
}
