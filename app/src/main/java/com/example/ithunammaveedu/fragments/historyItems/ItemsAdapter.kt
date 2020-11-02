package com.example.ithunammaveedu.fragments.historyItems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.OrderedItemViewBinding
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData

class HistoryItemsAdapter(var list: Array<FoodOrderData>): RecyclerView.Adapter<HistoryItemsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = OrderedItemViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= list[position]
        holder.bind(item)
    }

    class ViewHolder(val binding:OrderedItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(order: FoodOrderData){
            binding.ordered=order
        }
    }
}

