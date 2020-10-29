package com.example.ithunammaveedu.fragments.homefrag

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.FoodItemViewBinding


class Adapter(dataModelList: List<Food>, ctx: Context) : RecyclerView.Adapter<Adapter.ViewHolder>(), CustomClickListener {
    private var dataModelList: List<Food> = dataModelList
    private val context: Context = ctx
      fun setData( h:ArrayList<Food>){
          this.dataModelList=h
          notifyDataSetChanged()
      }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodItemViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel:Food= dataModelList[position]
        holder.bind(dataModel)
        //holder.itemRowBinding.itemClickListener = this
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    inner class ViewHolder(itemRowBinding: FoodItemViewBinding) : RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: FoodItemViewBinding = itemRowBinding
        fun bind(obj: Food) {
            itemRowBinding.food=obj
            itemRowBinding.executePendingBindings()
        }

    }

    override fun cardClicked(f: Food) {
        Toast.makeText(context, "You clicked " + f.foodName, Toast.LENGTH_LONG
        ).show()
    }

}
interface CustomClickListener {
    fun cardClicked(f: Food)
}