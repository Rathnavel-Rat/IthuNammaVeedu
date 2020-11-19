package com.example.ithunammaveedu.fragments.historyFrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHistoryBinding
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class history : Fragment() {

    lateinit var  binding:FragmentHistoryBinding
    lateinit var viewModel: HistoryViewModel
    lateinit var adapter:HistoryAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false)
        viewModel=ViewModelProvider(this).get(HistoryViewModel::class.java)

        binding.lifecycleOwner=this
        val dummy_data=ArrayList<HistoryItem>()
        adapter=HistoryAdapter(dummy_data,CancelClickListener{ cancelItem(it) }, ItemShowClickListener { this.findNavController().navigate(historyDirections.actionHistoryToHistoryItems(
            it.toTypedArray()
        )) })
        binding.adapter=adapter
        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {
            adapter.list=it
            adapter.notifyDataSetChanged()

        })

        return binding.root
    }
    fun cancelItem(id:String){
        val uid=FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().reference.child("Orders").child(uid).child(id).removeValue()
    }


}

