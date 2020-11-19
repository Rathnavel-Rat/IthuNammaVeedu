package com.example.ithunammaveedu.fragments.tabHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentBlankBinding
import com.example.ithunammaveedu.fragments.homefrag.*


class BlankFragment : Fragment() {
    lateinit var databinding:FragmentBlankBinding
    lateinit var viewModel:FragViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding=DataBindingUtil.inflate(inflater,R.layout.fragment_blank, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)

         val dummydata= emptyList<Food>()
         val adapter= Adapter(dummydata, AddClickListener { run { viewModel.apply { addAnItemToList(it) } } },
             SubClickListener { run { viewModel.subAnItemToList(it) } })
         databinding.adapter=adapter
         databinding.homeRecyclerView.addItemDecoration(DividerItemDecoration(this.activity, LinearLayout.VERTICAL))


         viewModel.foodHashMap.observe(this.requireActivity(), Observer { it ->
             val args = arguments
             val key = args!!.getString("keyValue")
             adapter.setData(it[key]!!)
         })



        return databinding.root

    }

}
