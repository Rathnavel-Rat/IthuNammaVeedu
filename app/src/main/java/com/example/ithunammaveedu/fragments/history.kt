package com.example.ithunammaveedu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHistoryBinding

class history : Fragment() {

    lateinit var  binding:FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false)
        return binding.root
    }


}

