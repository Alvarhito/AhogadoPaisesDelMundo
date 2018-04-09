package com.example.ceisutb01.ahogadopaisesdelmundo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main4.*


class Main4Activity : AppCompatActivity() {
    var ref = FirebaseDatabase.getInstance().reference
    var mensajeRef = ref.child("Servidor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        //modifica("Funciona")

    }
    /*
    fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        val prueba= Prueba as Button
        prueba.text="Se actualizo!!"

    }*/

    fun modifica(mensaje: String){
        mensajeRef.setValue(mensaje)
    }

    override fun onResume() {
        super.onResume()

        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions


        mensajeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                val prueba= prueba as TextView
                prueba.text=value
            }

            override fun onCancelled(databaseError: DatabaseError) {
                val prueba= prueba as TextView
                prueba.text="Error"
            }
        })
    }

}
