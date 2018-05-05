package com.example.ceisutb01.ahogadopaisesdelmundo

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main4.*
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.database.GenericTypeIndicator




class Main4Activity : AppCompatActivity() {
    var nombre="No hay nada"
    val Country = Pais()

    var names = ArrayList<String>()
    var capitals =ArrayList<String>()
    var regions = ArrayList<String>()
    var flags = ArrayList<String>()

    var ref = FirebaseDatabase.getInstance().reference

    var usuario=ale()

    var Fname = ref.child("Servicio")
    var Fcapitals = ref.child("Servicio")
    var Fregions = ref.child("Servicio")
    var Fflags = ref.child("Servicio")

    var mensajeRef = ref.child("Usuarios").child(usuario)
    var auu=mensajeRef.setValue("")
    var Existe=ref
    var Espera=ref
    var esp=true

    var PuedeEntrar=false

    var quien=""

    var newqst=FirebaseDatabase.getInstance().reference
    var exi=true

    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        MobileAds.initialize(this,"ca-app-pub-9105229171557561~6203725828")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        Servidor.setOnClickListener {
            quien="s"
            var l= arrayListOf<Int>(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
            val Prueba= prueba as TextView

            newqst = ref.child("Servicio").child(usuario)
            newqst.child("Esperar").child("p1").setValue("1")
            newqst.child("Esperar").child("p2").setValue("0")

            newqst.child("Puntos").child("puntos1").setValue("0")
            newqst.child("Puntos").child("puntos2").setValue("0")

            val aux=newqst.child("player1").setValue(l)
            val aux1=newqst.child("player2").setValue(l)
            //val auu=mensajeRef.setValue(newqst.key)
            val auun=mensajeRef.setValue(newqst.key)
            select()
            Cliente.visibility=View.INVISIBLE
            Cliente.isEnabled=false
            Servidor.text="Ir"
            //Prueba.text="Antes: "+Espera.key
            Prueba.text="Sala: "+usuario
            Handler().postDelayed(Runnable {
                PuedeEntrar=true
            }, 6000)
            Servidor.isEnabled=false
            Servidor.setTextColor(Color.GRAY)
            E.text="Esperando oponente..."
        }
        Cliente.setOnClickListener {
            quien="c"

            Servidor.visibility=View.INVISIBLE
            editText.visibility=View.VISIBLE
            Cliente.setOnClickListener {
                val mensaje = editText.getText().toString()
                val otro = ref.child("Usuarios").child(mensaje)
                Existe=Existe.child("Servicio")
                onResume()
                Cliente.isEnabled=false
                Cliente.setTextColor(Color.GRAY)
                Handler().postDelayed(Runnable {
                    val Prueba = prueba as TextView
                    if (exi) {
                        Espera=ref.child("Servicio").child(mensaje).child("Esperar").child("p1")
                        PuedeEntrar=true
                        Prueba.text = "Conectado a: "+otro.key
                        Cliente.text="Ir"
                        mensajeRef.setValue(otro.key)

                        /////////////////////////////////////////////////////////
                        Fname = Fname.child(mensaje).child("Palabras").child("names")
                        Fcapitals = Fcapitals.child(mensaje).child("Palabras").child("capitals")
                        Fregions = Fregions.child(mensaje).child("Palabras").child("regions")
                        Fflags = Fflags.child(mensaje).child("Palabras").child("flags")

                        Fname.addValueEventListener(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val t = object : GenericTypeIndicator<ArrayList<String>>() {}
                                val value = dataSnapshot.getValue(t)
                                names = value!!
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                val prueba = prueba as TextView
                                prueba.text = "Error"
                            }
                        })
                        Fcapitals.addValueEventListener(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val t = object : GenericTypeIndicator<ArrayList<String>>() {}
                                val value = dataSnapshot.getValue(t)
                                capitals = value!!
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                val prueba = prueba as TextView
                                prueba.text = "Error"
                            }
                        })
                        Fregions.addValueEventListener(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val t = object : GenericTypeIndicator<ArrayList<String>>() {}
                                val value = dataSnapshot.getValue(t)
                                regions = value!!
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                val prueba = prueba as TextView
                                prueba.text = "Error"
                            }
                        })
                        Fflags.addValueEventListener(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val t = object : GenericTypeIndicator<ArrayList<String>>() {}
                                val value = dataSnapshot.getValue(t)
                                flags = value!!
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                val prueba = prueba as TextView
                                prueba.text = "Error"
                            }
                        })

                        ref.child("Servicio").child(mensaje).child("Esperar").child("p2").setValue("1")
                        go("player2", "player1", mensaje)
                        ////////////////////////////////////////////////////////

                    } else {
                        Prueba.text = "No existe"
                        Handler().postDelayed(Runnable {
                            recreate()
                        }, 1000)

                    }
                }, 2000)

            }
        }
    }


    /*
    fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        val prueba= Prueba as Button
        prueba.text="Se actualizo!!"

    }*/
    fun select() {
        val url="https://restcountries.eu/rest/v2/all?fields=translations;capital;region;alpha2Code"
        getJson(url)
    }

    fun getJson(url: String) {

        //an extension over string (support GET, PUT, POST, DELETE with httpGet(), httpPut(), httpPost(), httpDelete())
        url.httpGet().responseJson{ request, response, result ->
            //do something with response
            when (result) {
                is Result.Failure -> {

                }
                is Result.Success -> {
                    val data = result.get()
                    Log.d("data", data.toString())
                    //var prueba= Prueba as TextView

                    Country.Organizar(data)
                    nombre=Country.getName()
                    //

                    ObtenerPaises()
                    var hola=newqst.child("Palabras")
                    hola.child("names").setValue(names)
                    hola.child("capitals").setValue(capitals)
                    hola.child("regions").setValue(regions)
                    hola.child("flags").setValue(flags)


                    //Toast.makeText(this,nombre.toString(), Toast.LENGTH_LONG).show()
                    //val boton_vn = Intent(this, MainActivity::class.java)
                    //startActivity(boton_vn)

                }
            }
        }
    }

    fun go(jugador: String,vs: String,sala: String){
        val boton = Intent(this, MainActivity::class.java)
        boton.putExtra("player",jugador)
        boton.putExtra("playerVs",vs)
        boton.putExtra("sala",sala)
        boton.putExtra("Pais", nombre)
        boton.putExtra("Reiniciar", false)

        boton.putExtra("conInternet", true)

        boton.putExtra("Continuar",false)

        Handler().postDelayed(Runnable {
            boton.putExtra("names",names)
            boton.putExtra("capitals",capitals)
            boton.putExtra("regions",regions)
            boton.putExtra("flags",flags)

            startActivity(boton)
            finish()
            //onDestroy()
        },1000)

    }

    fun ObtenerPaises(){
        var num=0
        var auxName=" "
        for (i in 0..1){
            while(true){
                num=(Math.random() * Country.nivel1.size).toInt()
                if(auxName != Country.nivel1[num].name){
                    auxName=Country.nivel1[num].name
                    break
                }
            }
            names.add(Country.nivel1[num].name)
            capitals.add(Country.nivel1[num].capital)
            regions.add(Country.nivel1[num].region)
            flags.add(Country.nivel1[num].flag)
        }


        /*num=(Math.random() * Country.nivel1.size).toInt()
        names.add(Country.nivel1[num].name)
        capitals.add(Country.nivel1[num].capital)
        regions.add(Country.nivel1[num].region)
        flags.add(Country.nivel1[num].flag)*/
        auxName=" "
        for (i in 0..1){
            while(true){
                num=(Math.random() * Country.nivel2.size).toInt()
                if(auxName != Country.nivel2[num].name){
                    auxName=Country.nivel2[num].name
                    break
                }
            }
            names.add(Country.nivel2[num].name)
            capitals.add(Country.nivel2[num].capital)
            regions.add(Country.nivel2[num].region)
            flags.add(Country.nivel2[num].flag)
        }
        /*num=(Math.random() * Country.nivel2.size).toInt()
        names.add(Country.nivel2[num].name)
        capitals.add(Country.nivel2[num].capital)
        regions.add(Country.nivel2[num].region)
        flags.add(Country.nivel2[num].flag)

        num=(Math.random() * Country.nivel2.size).toInt()
        names.add(Country.nivel2[num].name)
        capitals.add(Country.nivel2[num].capital)
        regions.add(Country.nivel2[num].region)
        flags.add(Country.nivel2[num].flag)*/
        auxName=" "
        for (i in 0..1){
            while(true){
                num=(Math.random() * Country.nivel3.size).toInt()
                if(auxName != Country.nivel3[num].name){
                    auxName=Country.nivel3[num].name
                    break
                }
            }
            names.add(Country.nivel3[num].name)
            capitals.add(Country.nivel3[num].capital)
            regions.add(Country.nivel3[num].region)
            flags.add(Country.nivel3[num].flag)
        }
        /*
        num=(Math.random() * Country.nivel3.size).toInt()
        names.add(Country.nivel3[num].name)
        capitals.add(Country.nivel3[num].capital)
        regions.add(Country.nivel3[num].region)
        flags.add(Country.nivel3[num].flag)

        num=(Math.random() * Country.nivel3.size).toInt()
        names.add(Country.nivel3[num].name)
        capitals.add(Country.nivel3[num].capital)
        regions.add(Country.nivel3[num].region)
        flags.add(Country.nivel3[num].flag)*/
        auxName=" "
        for (i in 0..1){
            while(true){
                num=(Math.random() * Country.nivel4.size).toInt()
                if(auxName != Country.nivel4[num].name){
                    auxName=Country.nivel4[num].name
                    break
                }
            }
            names.add(Country.nivel4[num].name)
            capitals.add(Country.nivel4[num].capital)
            regions.add(Country.nivel4[num].region)
            flags.add(Country.nivel4[num].flag)
        }
        /*
        num=(Math.random() * Country.nivel4.size).toInt()
        names.add(Country.nivel4[num].name)
        capitals.add(Country.nivel4[num].capital)
        regions.add(Country.nivel4[num].region)
        flags.add(Country.nivel4[num].flag)

        num=(Math.random() * Country.nivel4.size).toInt()
        names.add(Country.nivel4[num].name)
        capitals.add(Country.nivel4[num].capital)
        regions.add(Country.nivel4[num].region)
        flags.add(Country.nivel4[num].flag)*/

    }

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

        Existe.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val t = object : GenericTypeIndicator<ArrayList<String>>() {}
                //val value = dataSnapshot.getValue(t)
                //val Prueba= prueba as TextView
                val mensaje = editText.getText().toString()
                /*
                exi=false
                for(i in 0..value!!.size-1){
                    if(value!![i].toString()==mensaje.toString()){
                        exi=true
                        break
                    }
                }*/

                if(dataSnapshot.child(mensaje).exists()){
                    exi=true
                }else{
                    exi=false
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                val prueba= prueba as TextView
                prueba.text="Error"
            }
        })


        Espera.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(PuedeEntrar) {

                    //val value = dataSnapshot.getValue(String::class.java)
                    //val prueba= prueba as TextView
                    //
                    var value=dataSnapshot.child("Servicio").child(usuario).child("Esperar").child("p2").getValue(String::class.java)
                     //prueba.text="Estoy accediendo a: "+

                    if (quien == "s") {
                        value=dataSnapshot.child("Servicio").child(usuario).child("Esperar").child("p2").getValue(String::class.java)
                        if(value=="1") {
                            if (esp) {
                                //Servidor.isEnabled = true
                                //Servidor.setTextColor(Color.WHITE)
                                go("player1", "player2", usuario)
                                esp = false
                            }
                             }
                    }else if (quien == "c") {
                        value=dataSnapshot.child("Servicio").child(usuario).child("Esperar").child("p1").getValue(String::class.java)
                        if(value=="1"){
                            //Cliente.isEnabled = true
                            //Cliente.setTextColor(Color.WHITE)
                        }
                    }
                    /*
                    if (value == "1") {
                        if (quien == "s") {
                            Servidor.isEnabled = true
                            Servidor.setTextColor(Color.WHITE)
                        }
                        if (quien == "c") {
                            Cliente.isEnabled = true
                            Cliente.setTextColor(Color.WHITE)
                        }
                    }
                    */
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                val prueba= prueba as TextView
                prueba.text="Error"
            }
        })

    }

}
