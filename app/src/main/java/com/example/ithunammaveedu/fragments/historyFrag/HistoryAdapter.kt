package com.example.ithunammaveedu.fragments.historyFrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.HistoryItemViewBinding
import com.example.ithunammaveedu.fragments.cartFrag.PlaceOrder

class HistoryAdapter(var list:ArrayList<HistoryItem>, var cancelClickListener: CancelClickListener):RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val order= list[position]
        holder.bind(order,cancelClickListener)
    }

    class ViewHolder(val binding:HistoryItemViewBinding ): RecyclerView.ViewHolder(binding.root) {
        fun bind(order: HistoryItem, action:CancelClickListener){
            binding.orderdata=order
            binding.cancelorder=action

        }
    }
}

class CancelClickListener(var clickListener: (item: String) -> Unit){
    fun onClick(item:String) =clickListener(item)
}
