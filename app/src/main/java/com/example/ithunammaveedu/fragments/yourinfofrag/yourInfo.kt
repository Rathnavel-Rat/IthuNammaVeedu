package com.example.ithunammaveedu.fragments.yourinfofrag

import android.content.Context
import android.content.SharedPreferences
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
import com.google.android.material.snackbar.Snackbar

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
            if(binding.name.text.toString().isEmpty() && binding.phonenumber.text.toString().isEmpty() && binding.address.text.toString().isEmpty() ){
                Snackbar.make(this.requireView(),"please Fill All",Snackbar.LENGTH_SHORT).show()
            }
            else {
                viewModel.updatedValue(
                    binding.name.text.toString(),
                    binding.phonenumber.text.toString(),
                    binding.address.text.toString()
                )
                val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences("INV.PrefrenceFile", Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("firstTime",true).apply()
            }
        }
        return binding.root

    }


}
