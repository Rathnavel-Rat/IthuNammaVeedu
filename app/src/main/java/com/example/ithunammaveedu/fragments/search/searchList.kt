package com.example.ithunammaveedu.fragments.search

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentSearchListBinding
import com.example.ithunammaveedu.fragments.homefrag.FragViewModel
import com.example.ithunammaveedu.fragments.tabHome.AddClickListener
import com.example.ithunammaveedu.fragments.tabHome.Food
import com.example.ithunammaveedu.fragments.tabHome.SubClickListener


class searchList : Fragment() {
    lateinit var viewModel:FragViewModel
    lateinit var binding:FragmentSearchListBinding
    lateinit var adapter: Adapter
    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_search_list, container, false)
        viewModel= ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
         adapter= Adapter(AddClickListener { run { viewModel.apply { addAnItemToList(it) ;} } },
             SubClickListener { run { viewModel.subAnItemToList(it) } })
        binding.adapter=adapter

        binding.searchRecycler.addItemDecoration(
            DividerItemDecoration(
                this.activity,
                LinearLayout.VERTICAL
            )
        )

        viewModel.foodHashMap.observeOnce(this.requireActivity(), Observer { it ->
            adapter.setitem(it.values.flatten())
        })





        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchView:SearchView=menu.findItem(R.id.app_bar_search).actionView  as SearchView
        searchView.isIconified=false
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                viewModel.foodHashMap.observe(this@searchList.viewLifecycleOwner, Observer {
                    adapter.addHeaderAndSubmitList(it.values.flatten())
                    adapter.filter.filter(query)
                })

                return false
            }
        })
        searchView.setOnCloseListener {
            this@searchList.findNavController().navigate(R.id.action_searchList_to_home2)
            return@setOnCloseListener true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

