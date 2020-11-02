package com.example.ithunammaveedu.fragments.historyFrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHistoryBinding
import com.example.ithunammaveedu.fragments.cartFrag.PlaceOrder
import com.google.firebase.FirebaseApiNotAvailableException
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
        adapter=HistoryAdapter(dummy_data,CancelClickListener{
            cancelItem(it)
        })
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

