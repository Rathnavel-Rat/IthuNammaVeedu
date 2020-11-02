package com.example.ithunammaveedu.fragments.homefrag

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHomeBinding


class Home : Fragment() {
   lateinit var binding:FragmentHomeBinding
    lateinit var viewModel: FragViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        binding.lifecycleOwner=this

        val adapter=Adapter(AddClickListener{ run { viewModel.apply { addAnItemToList(it) }  } }, SubClickListener { run{ viewModel.subAnItemToList(it)}})
        binding.adapter=adapter
        binding.homeRecyclerView.addItemDecoration(DividerItemDecoration(this.activity, LinearLayout.VERTICAL))
        viewModel.foodList.observe(viewLifecycleOwner, Observer {
            adapter.addHeaderAndSubmitList(it)
        })
        viewModel.enableButton.observe(viewLifecycleOwner, Observer {
            binding.P2B.isEnabled=it
        })
        binding.P2B.setOnClickListener {
            this.findNavController().navigate(R.id.action_home2_to_cart)
        }




        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cartmenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear->{  }
        }
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController()) ||    super.onOptionsItemSelected(item)
    }

}
