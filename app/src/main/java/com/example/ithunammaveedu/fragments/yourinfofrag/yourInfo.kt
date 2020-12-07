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
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: FragmentYourInfoBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_your_info, container, false)
        binding.lifecycleOwner = this
        viewModel=ViewModelProvider(this).get(YourinfoViewModel::class.java)
        sharedPreferences = this.requireActivity().getSharedPreferences("INV.PrefrenceFile", Context.MODE_PRIVATE)
        val name= viewModel.getValues()
        sharedPreferences.edit().putString("username",name).apply()


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

                sharedPreferences.edit().putBoolean("firstTime",true).apply()

                sharedPreferences.edit().putString("username",binding.name.text.toString()).apply()

            }
        }
        return binding.root

    }


}
