package com.example.ithunammaveedu.fragments.homefrag

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Home : Fragment() {
   lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        Log.i("ho","oncreateaView")


        val viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        binding.lifecycleOwner=this

        val adapter=Adapter(AddClickListener{ run { viewModel.apply { addAnItemToList(it) }  } }, SubClickListener { run{ viewModel.subAnItemToList(it)}})
        binding.adapter=adapter

        viewModel.foodList.observe(viewLifecycleOwner, Observer {
            adapter.addHeaderAndSubmitList(it)
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
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController()) ||    super.onOptionsItemSelected(item)
    }

}
