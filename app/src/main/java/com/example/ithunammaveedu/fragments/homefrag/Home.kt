package com.example.ithunammaveedu.fragments.homefrag

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHomeBinding


class Home : Fragment() {
   lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        Log.i("hom","oncreate")
        val food_list= arrayListOf<Food>()
        val viewModel=ViewModelProvider(this).get(FragViewModel::class.java)
        val adapter=Adapter(food_list, AddClickListener{ run { viewModel.apply { addAnItemToList(it) }  } }, SubClickListener { run{ viewModel.subAnItemToList(it)}})
        binding.adapter=adapter
        viewModel.fetchContacts()
        viewModel.foodList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })



        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cartmenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController()) ||    super.onOptionsItemSelected(item)
    }

}
