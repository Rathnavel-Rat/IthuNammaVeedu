package com.example.ithunammaveedu.fragments.yourinfofrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentYourInfoBinding

class yourInfo : Fragment() {
    lateinit var  viewModel: YourinfoViewModel
    lateinit var binding: FragmentYourInfoBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_your_info, container, false)
        binding.lifecycleOwner = this
        viewModel=ViewModelProvider(this).get(YourinfoViewModel::class.java)
        viewModel.getValues()
        binding.viewModel=viewModel
        binding.saveUserInfo.setOnClickListener {
            val name=binding.name.text.toString()
            val phone=binding.phonenumber.text.toString()
            val address=binding.address.text.toString()
            viewModel.updatedValue(name,phone,address)

        }
        return binding.root

    }


}
