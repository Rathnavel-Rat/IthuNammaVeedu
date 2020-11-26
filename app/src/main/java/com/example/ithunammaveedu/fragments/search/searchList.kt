package com.example.ithunammaveedu.fragments.search

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentSearchListBinding
import com.example.ithunammaveedu.fragments.homefrag.FragViewModel
import com.example.ithunammaveedu.fragments.tabHome.AddClickListener
import com.example.ithunammaveedu.fragments.tabHome.SubClickListener


class searchList : Fragment() {
    lateinit var viewModel:FragViewModel
    lateinit var binding:FragmentSearchListBinding
    lateinit var adapter: Adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_search_list, container, false)
        viewModel= ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        adapter= Adapter(AddClickListener { run { viewModel.addAnItemToList(it); notifyAdapter()}},SubClickListener { run { viewModel.subAnItemToList(it);notifyAdapter() } })

        when(requireArguments().getString("type")){
            "search" ->{ viewModel.foodHashMap.observeOnce(this.requireActivity(), Observer { adapter.setitem(it.values.flatten()) }) }
            "veg"    ->{viewModel.foodHashMap.observeOnce(this.requireActivity(), Observer {  adapter.setitem( it.values.flatten().filter { i-> i.type=="veg" }) ; })}
            "nonveg" ->{viewModel.foodHashMap.observeOnce(this.requireActivity(), Observer {  adapter.setitem( it.values.flatten().filter { i-> i.type=="nonveg" })})}
        }


        binding.adapter=adapter
        binding.searchRecycler.addItemDecoration(DividerItemDecoration(this.activity, LinearLayout.VERTICAL))


        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        searchImplement(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun searchImplement(menu: Menu) {
        if (requireArguments().getString("type") == "search") {
            val searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
            searchView.isIconified = false
            searchView.requestFocus()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    adapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    adapter.filter.filter(query)
                    return false
                }
            })
            searchView.setOnCloseListener {
                this@searchList.findNavController().navigate(R.id.action_searchList_to_home2)
                return@setOnCloseListener true
            }
        }
    }

    fun notifyAdapter(){
        adapter.notifyDataSetChanged()
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

