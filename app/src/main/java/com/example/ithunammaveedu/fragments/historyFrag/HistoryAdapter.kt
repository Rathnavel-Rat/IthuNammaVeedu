package com.example.ithunammaveedu.fragments.historyFrag

import DiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.HistoryItemViewBinding
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData


class HistoryAdapter(var list:ArrayList<HistoryItem>, var cancelClickListener: CancelClickListener,var itemShowClickListener: ItemShowClickListener):RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    init {
        setHasStableIds(false)
    }
    fun setData(model:ArrayList<HistoryItem>){
        val diffResult = DiffUtil.calculateDiff(DiffCallback(list,model))
        list.clear()
        list.addAll(model)
        diffResult.dispatchUpdatesTo(this)
    }

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
        holder.bind(order,cancelClickListener,itemShowClickListener)
    }

    class ViewHolder(val binding:HistoryItemViewBinding ): RecyclerView.ViewHolder(binding.root) {
        fun bind(order: HistoryItem, action: CancelClickListener, itemShowClickListener: ItemShowClickListener){
            binding.orderdata=order
            binding.cancelorder=action
            binding.passitems=itemShowClickListener

        }
    }
}

class CancelClickListener(var clickListener: (item: String) -> Unit){
    fun onClick(item:String) =clickListener(item)
}

class ItemShowClickListener(var clickListener: (item: ArrayList<FoodOrderData>) -> Unit)
{
    fun onClick(item:ArrayList<FoodOrderData>)=clickListener(item)
}
