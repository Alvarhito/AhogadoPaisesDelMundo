package com.example.ceisutb01.ahogadopaisesdelmundo

import android.content.Intent
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
import java.util.*


object Constants {
    @JvmStatic
    val FIREBASE_ITEM: String = "Servicio"
}
class Main4Activity : AppCompatActivity() {
    var ref = FirebaseDatabase.getInstance().reference

    var usuario=ale()

    var mensajeRef = ref.child("Usuarios").child(usuario)
    var auu=mensajeRef.setValue("")

    var otro=mensajeRef


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        Servidor.setOnClickListener {
            val newqst = ref.child(Constants.FIREBASE_ITEM).push()
            val aux=newqst.child("player1") .setValue("Primer Jugador")
            val aux1=newqst.child("player2").setValue("Segundo Jugador")
            val auu=mensajeRef.setValue(newqst.key)
        }
        Cliente.setOnClickListener {

            val mensaje = editText.getText().toString()
            mensajeRef = ref.child("Usuarios").child(mensaje)

        }


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
    fun ale() :String{
        val random = Random()
        var num=""
        for (i in 0..3){
            num += (random.nextInt(9 - 0)).toString()
        }
        return  num
    }

    /*
    override fun onDestroy() {
        //var hola0=newqst.child(usuario).removeValue()
        var hola=newqst.child("player1").removeValue()
        var hola1=newqst.child("player2").removeValue()
        super.onDestroy()
    }*/
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
                //val prueba= prueba as TextView
                otro.setValue(value)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                val prueba= prueba as TextView
                prueba.text="Error"
            }
        })
    }

}
