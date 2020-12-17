package com.example.ithunammaveedu.fragments.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.ithunammaveedu.R
import com.example.ithunammaveedu.fragments.cartFrag.PlaceOrder
import com.example.ithunammaveedu.fragments.historyFrag.HistoryItem
import com.example.ithunammaveedu.fragments.historyItems.historyItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class notification : Service() {
    private val CHANNEL_ID="INV"
    override fun onBind(intent: Intent?): IBinder? {

        return null;
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val firebaseAuth=FirebaseAuth.getInstance().currentUser
        if(firebaseAuth!=null){
        val db= FirebaseDatabase.getInstance().reference.child("Orders").child(firebaseAuth!!.uid)
        var arrayList=ArrayList<HistoryItem>()
        db.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children ) {
                    val c = data.getValue(PlaceOrder::class.java)
                    val d = HistoryItem(snapId = data.key!!,dataItems = c!!)
                    arrayList.add(d)
                }
            }

        })
        db.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList1=ArrayList<HistoryItem>()

                for(data in snapshot.children ) {
                    val c = data.getValue(PlaceOrder::class.java)
                    val d = HistoryItem(snapId = data.key!!,dataItems = c!!)
                    arrayList1.add(d)
                }
                val j=arrayList1.minus(arrayList)
                println("list $j")
                j.forEach {

                    mNotificationManager.notify( 0, builder(it.dataItems.status)!!.build())
                }

               arrayList=arrayList1
            }

        })
        }

        return START_STICKY

    }
    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name ="INV"
            val descriptionText =" getString()"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun builder(content:String): NotificationCompat.Builder? {
         return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle)
            .setContentTitle("Ithu Nama Vedu")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}