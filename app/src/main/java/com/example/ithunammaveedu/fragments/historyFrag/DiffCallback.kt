package com.example.ithunammaveedu.fragments.historyFrag

import androidx.recyclerview.widget.DiffUtil

class MyDiffCallback(newPersons: List<HistoryItem>, oldPersons: List<HistoryItem>) : DiffUtil.Callback() {
    var oldPersons: List<HistoryItem>
    var newPersons: List<HistoryItem>
    override fun getOldListSize(): Int {
        return oldPersons.size
    }

    override fun getNewListSize(): Int {
        return newPersons.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPersons[oldItemPosition].snapId == newPersons[newItemPosition].snapId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPersons[oldItemPosition] == newPersons[newItemPosition]
    }


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        this.newPersons = newPersons
        this.oldPersons = oldPersons
    }
}