package com.example.ithunammaveedu.fragments.yourinfofrag

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentYourInfoBinding
import com.example.ithunammaveedu.fragments.homefrag.HomeDirections
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
            if(binding.name.text.toString().trim().isEmpty() || binding.phonenumber.text.toString().trim().isEmpty() || binding.address.text.toString().trim().isEmpty() ){
                try{
                     binding.name.text.toString().toInt()
                }
                catch (e:Exception){
                    Snackbar.make(this.requireView(),"enter",Snackbar.LENGTH_SHORT).show()
                }
                if( binding.phonenumber.text.toString().length != 10)
                    Snackbar.make(this.requireView(),"enter your mobile number",Snackbar.LENGTH_SHORT).show()
                else{
                Snackbar.make(this.requireView(),"please Fill All",Snackbar.LENGTH_SHORT).show()}
            }
            else {
                viewModel.updatedValue(
                    binding.name.text.toString(),
                    binding.phonenumber.text.toString(),
                    binding.address.text.toString()

                )

                sharedPreferences.edit().putBoolean("firstTime",true).commit()

                sharedPreferences.edit().putString("username",binding.name.text.toString()).commit()
                Toast.makeText(this.requireActivity(),"details updated",Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(yourInfoDirections.actionYourInfoToHome2())


            }
        }
        return binding.root

    }


}
