package com.example.ithunammaveedu.fragments.homefrag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ithunammaveedu.databinding.FoodItemViewBinding
import com.example.ithunammaveedu.databinding.HeaderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class Adapter(private val itemAddClickListener: AddClickListener, private val itemSubClickListener: SubClickListener ) : ListAdapter<DataItem, RecyclerView.ViewHolder>(FoodItemDiffutilCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)


    fun addHeaderAndSubmitList(list: ArrayList<Food>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header("Loading"))
                else -> {
                    val groupedList = list.groupBy { it.category }
                    var myList = ArrayList<DataItem>()
                    for(i in groupedList.keys)
                    {
                        myList.add(DataItem.Header(i))
                        for(v in groupedList.getValue(i))
                        {
                            myList.add(DataItem.ProductItem(v))
                        }
                    }
                    myList
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val food = getItem(position) as DataItem.ProductItem
                holder.bind(food.FoodItem,itemAddClickListener,itemSubClickListener)
            }
            is TextViewHolder->{
                val headerItem = getItem(position) as DataItem.Header
                holder.bind(headerItem.type)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ProductItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
    class TextViewHolder(val binding: HeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(header:String){
            binding.headers=header
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderBinding.inflate(layoutInflater,parent,false)
                return TextViewHolder(binding)
            }
        }
    }


    class ViewHolder(val binding: FoodItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, itemAddClickListener: AddClickListener, itemSubClickListener: SubClickListener) {
            binding.food = food
            binding.addclicklistener=itemAddClickListener
            binding.subclicklistener=itemSubClickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FoodItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }




}

class AddClickListener(val clickListener: (food:Food) -> Unit) {
    fun onClick(item:Food)= clickListener(item)
}

class SubClickListener(val clickListener: (food: Food) -> Unit) {
    fun onClick(item:Food) = clickListener(item)
}


class FoodItemDiffutilCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem{
    data class ProductItem(val FoodItem:Food):DataItem(){
        override val id = FoodItem.id.toLong()
    }
    data class  Header(val type:String):DataItem(){
        override val id = Long.MIN_VALUE
    }
    abstract val id:Long;
}
