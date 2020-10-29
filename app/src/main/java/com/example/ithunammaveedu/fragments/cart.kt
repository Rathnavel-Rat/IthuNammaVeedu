package com.example.ithunammaveedu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentCartBinding

/**
 * A simple [Fragment] subclass.
 */
class cart : Fragment() {
    lateinit var binding:FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)
       return binding.root
    }

}
