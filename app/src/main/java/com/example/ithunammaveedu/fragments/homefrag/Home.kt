package com.example.ithunammaveedu.fragments.homefrag

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ithunammaveedu.MainActivity
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.Starter.LoginActivity
import com.example.ithunammaveedu.databinding.FragmentHomeBinding
import com.example.ithunammaveedu.fragments.tabHome.BlankFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.*


class Home : Fragment() {
   lateinit var binding:FragmentHomeBinding
    lateinit var viewModel: FragViewModel
    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        viewPagerAdapter=ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle,0)

        binding.lifecycleOwner=this

        viewModel.enableButton.observe(viewLifecycleOwner, Observer {
            binding.P2B.isEnabled=it
        })
        binding.P2B.setOnClickListener {
            this.findNavController().navigate(R.id.action_home2_to_cart)
        }
        binding.pager.adapter= viewPagerAdapter
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        viewModel.tab_headers.observe(this.requireActivity(), Observer {
            viewPagerAdapter.sizeChanged(it.size)})


        TabLayoutMediator(binding.tabLayout,binding.pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                viewModel.tab_headers.observe(this.requireActivity(), Observer {
                    tab.text=it[position]

                })
            }).attach()



        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cartmenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.signout->{
                val auth=FirebaseAuth.getInstance()
                val intent= Intent(this.requireActivity(), LoginActivity::class.java)
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
                googleSignInClient.signOut()
                auth.signOut()
                startActivity(intent)
                this.requireActivity().finish()
        }}

        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController()) ||    super.onOptionsItemSelected(item)


}

    inner class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,  size:Int): FragmentStateAdapter(fm,lifecycle){
        var size=size
        override fun getItemCount(): Int{
            return size
        }
        fun sizeChanged(size:Int){
            this.size=size
            notifyDataSetChanged()
        }

        override fun createFragment(position: Int): Fragment {
            var fragment:Fragment?=null
            fragment= BlankFragment()
            val Bundle=Bundle()
            Bundle.putInt("position",position)
            fragment.arguments=Bundle

            return fragment
        }

    }
}
