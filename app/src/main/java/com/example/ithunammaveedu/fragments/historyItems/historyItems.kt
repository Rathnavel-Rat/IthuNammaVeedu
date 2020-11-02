package com.example.ithunammaveedu.fragments.historyItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHistoryItemsBinding

/**
 * A simple [Fragment] subclass.
 */
class historyItems : Fragment() {
    lateinit var binding:FragmentHistoryItemsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_history_items, container, false)
        val args= arguments?.let { historyItemsArgs.fromBundle(it) }
        val adapter=HistoryItemsAdapter(args!!.items)
        binding.adapter=adapter
        return  binding.root
    }

}
