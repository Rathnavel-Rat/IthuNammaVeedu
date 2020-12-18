package com.example.ithunammaveedu.fragments.cartFrag

//import android.icu.text.SimpleDateFormat
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.databinding.FragmentCartBinding
import com.example.ithunammaveedu.fragments.homefrag.FoodOrderData
import com.example.ithunammaveedu.fragments.homefrag.FragViewModel
import com.example.ithunammaveedu.fragments.yourinfofrag.user_Info
import com.example.ithunammaveedu.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class cart : Fragment() {
    lateinit var binding:FragmentCartBinding
    var total by Delegates.notNull<Int>()
    lateinit var  dummy_data:ArrayList<FoodOrderData>
    lateinit var viewModel:FragViewModel
    lateinit var  sharedPreferences: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)
        binding.lifecycleOwner=this
        viewModel=ViewModelProvider(requireActivity()).get(FragViewModel::class.java)
        viewModel.setCartItems()
        dummy_data= ArrayList<FoodOrderData>()
        sharedPreferences= this.requireActivity().getSharedPreferences("INV.PrefrenceFile", Context.MODE_PRIVATE)
        val adapter=CartAdapter(dummy_data,AddClickListener { run{viewModel.increamentCartItem(it)} },SubClickListener{ run{ viewModel.decreamentCartItem(it)} }, RemoveClickListener { run { viewModel.removeAnCartitem(it) }  })
        binding.adapter=adapter
        println("ak47 ${sharedPreferences.getString("username","unknown")}")

        viewModel.foodCart.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            if(dummy_data.isEmpty()){
                dummy_data.addAll(it) //for history these are used in  place order function given below
            }
            else{
                dummy_data.removeAll(dummy_data)
                dummy_data.addAll(it)
            }

        })

        viewModel.total_price.observe(viewLifecycleOwner, Observer {
            binding.PlaceOrder.text="plcaeOrder:"+ resources.getString(R.string.rupees)+"$it"
            total=it
        })

        viewModel.enableButton.observe(viewLifecycleOwner, Observer {
            binding.PlaceOrder.isEnabled=it
            if(it==false) {
                binding.imageView.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE
                binding.PlaceOrder.visibility=View.GONE
            }
            else{

                binding.imageView.visibility = View.GONE
                binding.textView.visibility = View.GONE
                binding.PlaceOrder.visibility=View.VISIBLE

            }        })

        binding.PlaceOrder.setOnClickListener {
            val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences("INV.PrefrenceFile", Context.MODE_PRIVATE)
            if(!sharedPreferences.getBoolean("firstTime",false)){
                this.findNavController().navigate(R.id.action_cart_to_yourInfo)
            }
            else {
                placeOrderAction()
                dialog()
            }

        }
        return binding.root
    }

    fun dialog(){
        val dialog=Dialog(this.requireContext())
        dialog.setContentView(R.layout.popup)
        dialog.show()
        val button=dialog.findViewById<Button>(R.id.done)
        button.setOnClickListener {
            viewModel.clearAll()
            this.findNavController().navigate(R.id.action_cart_to_home2)
            dialog.hide()
        }
    }

    fun placeOrderAction(){
        val placeOrder=PlaceOrder()
        val uid=FirebaseAuth.getInstance().currentUser!!.uid
        val d_info=FirebaseDatabase.getInstance().reference.child("userInfo").child(uid)
        val info=ArrayList<user_Info>()
        d_info.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(user_Info::class.java)?.let { info.add(it) }
                val Date= DateFormat.getDateInstance().format(Date()).toString()
                placeOrder.date=Date
                placeOrder.address=info.first().address
                placeOrder.phoneNumber=info.first().phone
                placeOrder.total= total.toString()
                placeOrder.foodItem=dummy_data
                placeOrder.status="Placing Order ...."
                placeOrder.name=sharedPreferences.getString("username","unknown")!!
                FirebaseDatabase.getInstance().reference.child("Orders").child(uid).push().setValue(placeOrder)
            }

        })

    }

}
