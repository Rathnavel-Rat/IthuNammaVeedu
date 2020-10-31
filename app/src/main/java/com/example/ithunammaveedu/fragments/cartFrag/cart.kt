package com.example.ithunammaveedu.fragments.cartFrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentCartBinding
import com.example.ithunammaveedu.fragments.homefrag.FragViewModel
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData

/**
 * A simple [Fragment] subclass.
 */
class cart : Fragment() {
    lateinit var binding:FragmentCartBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding=DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)
        binding.lifecycleOwner=this
        val viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        viewModel.setCartItems()
        val dummy_data= ArrayList<FoodOrderData>()
        val adapter=CartAdapter(dummy_data,AddClickListener { run{viewModel.increamentCartItem(it)} },SubClickListener{ run{ viewModel.decreamentCartItem(it)} }, RemoveClickListener { run { viewModel.removeAnFromitem(it) }  })
        binding.adapter=adapter
        viewModel.foodCart.observe(viewLifecycleOwner, Observer {
            println("pooja $it")
            adapter.setDaa(it)

        })
        return binding.root
    }

}
