package com.example.ithunammaveedu.fragments.yourinfofrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class YourinfoViewModel: ViewModel() {
    private val auth=FirebaseAuth.getInstance().currentUser!!
    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() =_username

    private var _mail=MutableLiveData<String>()
    val  mail:LiveData<String>
      get() =_mail
    private var _address=MutableLiveData<String>()
    val  address:LiveData<String>
      get() = _address
    private var _phone=MutableLiveData<String>()
    val phone:LiveData<String>
      get() = _phone
    private  var info=user_Info()
     fun getValues(){
         val database = FirebaseDatabase.getInstance().reference.child("userInfo").child(auth.uid)
         database.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {
                 info= dataSnapshot.getValue(user_Info::class.java)!!
                 _username.value=info.name
                 _address.value= info.address.toString()
                 _mail.value=info.mail
                 _phone.value=info.phone

             }

             override fun onCancelled(error: DatabaseError) {

             }
         })

     }
    fun updatedValue(name:String,phone:String,address:String){
        val data= user_Info(auth.email!!,phone,name,address)
        FirebaseDatabase.getInstance().reference.child("userInfo").child(auth.uid).setValue(data)
    }

}