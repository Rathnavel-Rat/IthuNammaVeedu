

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T>(val oldList:List<T>,val newList: List<T>): DiffUtil.Callback() {
    private var OldList = listOf<T>()
    private var NewList = listOf<T>()
    init {
        OldList=oldList
        NewList=newList
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  newList[newItemPosition] == oldList[oldItemPosition];
    }

    override fun getOldListSize(): Int {
        return  OldList.size
    }

    override fun getNewListSize(): Int {
        return  NewList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList == oldList
    }

}