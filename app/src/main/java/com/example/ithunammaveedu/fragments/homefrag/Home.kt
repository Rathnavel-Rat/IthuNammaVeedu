package com.example.ithunammaveedu.fragments.homefrag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.Starter.LoginActivity
import com.example.ithunammaveedu.databinding.FragmentHomeBinding
import com.example.ithunammaveedu.fragments.service.notification
import com.example.ithunammaveedu.fragments.tabHome.BlankFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class Home : Fragment() {
   lateinit var binding:FragmentHomeBinding
    lateinit var viewModel: FragViewModel
    lateinit var viewPagerAdapter: ViewPagerAdapter
    var isfloatMenuOpen =false
    lateinit var  sharedPreferences:SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        viewPagerAdapter=ViewPagerAdapter(childFragmentManager, lifecycle, 0, emptyArray())
        sharedPreferences = this.requireActivity().getSharedPreferences("INV.PrefrenceFile", Context.MODE_PRIVATE)
        binding.fabHandler=FabHandler()
        closeFabMenu()
        binding.lifecycleOwner=this

        viewModel.enableButton.observe(viewLifecycleOwner, Observer {
            binding.P2B.isEnabled = it
        })

        binding.P2B.setOnClickListener {
            this.findNavController().navigate(R.id.action_home2_to_cart)
        }

        binding.pager.adapter= viewPagerAdapter
        binding.pager.setPageTransformer(ZoomOutPageTransformer())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.colorBlack,null))
        }
        else{
                binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.colorBlack))

        }

        viewModel.foodHashMap.observeOnce(this.requireActivity(), Observer { it ->
            binding.progressBar.visibility = View.GONE
            viewPagerAdapter.dataChanged(it.keys.size, it.keys.toTypedArray())
            TabLayoutMediator(binding.tabLayout, binding.pager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                 val tv=TextView(this.requireContext())
                tv.text= it.keys.toTypedArray()[position]
                val typeface = ResourcesCompat.getFont(this.requireContext(), R.font.orbitron_medium)
                tv.typeface=typeface
                tab.customView=tv

            }).attach()

        })
        binding.tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#33ffff"));
        binding.tabLayout.background=ResourcesCompat.getDrawable(resources,R.color.colorPrimary,null)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cartmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun expandFabMenu() {
        ViewCompat.animate(binding.floatingActionButton).rotation(80.0f).withLayer().setDuration(300).setInterpolator(OvershootInterpolator(10.0f)).start()
        binding.veg.startAnimation(AnimationUtils.loadAnimation(this.requireContext(), R.anim.fabopen))
        binding.nonveg.startAnimation(AnimationUtils.loadAnimation(this.requireContext(), R.anim.fabopen))
        binding.veg.isClickable = true
        binding.nonveg.isClickable = true
        isfloatMenuOpen = true
    }
    private fun closeFabMenu() {
        ViewCompat.animate(binding.floatingActionButton).rotation(0.0f).withLayer().setDuration(300).setInterpolator(OvershootInterpolator(10.0f)).start()
        binding.veg.startAnimation(AnimationUtils.loadAnimation(this.requireContext(), R.anim.fabclose))
        binding.nonveg.startAnimation(AnimationUtils.loadAnimation(this.requireContext(), R.anim.fabclose))
        binding.veg.isClickable = false
        binding.nonveg.isClickable = false
        isfloatMenuOpen = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.signout -> {
                val intentService = Intent(this.requireActivity(), notification::class.java)
                 this.requireActivity().stopService(intentService)
                val auth = FirebaseAuth.getInstance()
                val intent = Intent(this.requireActivity(), LoginActivity::class.java)
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
                googleSignInClient.signOut()
                auth.signOut()
                sharedPreferences.edit().putBoolean("firstTime",false).commit()
                startActivity(intent)
                this.requireActivity().finish()

            }
            R.id.search -> this@Home.findNavController().navigate(HomeDirections.actionHome2ToSearchList(type = "search"))
        }

        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||    super.onOptionsItemSelected(item)

}
   inner class FabHandler {
        fun onBaseFabClick(view: View?) {
            if (isfloatMenuOpen)
                closeFabMenu()
            else
                expandFabMenu()
        }

        fun onVegClick(view: View?) {
            this@Home.view?.let {
                Snackbar.make(view!!, "Veg", Snackbar.LENGTH_SHORT).show()
                this@Home.findNavController().navigate(HomeDirections.actionHome2ToSearchList(type = "veg"))
            }
        }

        fun onNonVegClick(view: View?) {
            this@Home.view?.let {
                Snackbar.make(it, "NON-Veg", Snackbar.LENGTH_SHORT).show()
                this@Home.findNavController().navigate(HomeDirections.actionHome2ToSearchList(type = "nonveg"))
            }
        }
    }

}

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var size: Int, private var keys: Array<String>): FragmentStateAdapter(fm, lifecycle){

    override fun getItemCount(): Int{
        return size
    }
    fun dataChanged(size: Int, keys: Array<String>){
        this.size=size
        this.keys=keys
        notifyDataSetChanged()
    }


    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment?=null
        fragment= BlankFragment()
        val Bundle=Bundle()
        Bundle.putString("keyValue", keys[position])
        fragment.arguments=Bundle
        return fragment
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
