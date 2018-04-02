package com.example.ceisutb01.ahogadopaisesdelmundo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.ArrayList
import android.widget.LinearLayout
import android.graphics.Bitmap

class Main2Activity : AppCompatActivity() {
    var nombre="No hay nada"
    val Country = Pais()
    var continuar=false

    var names = ArrayList<String>()
    var capitals =ArrayList<String>()
    var regions = ArrayList<String>()
    var flags = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val Tpuntos=prefs.getString("puntos","0").toInt()

        if(Tpuntos>0){
            Continuar.visibility=View.VISIBLE
        }
        Continuar.setOnClickListener{
            continuar=true
            selectPais()
        }

        playButton.setOnClickListener{
            //val layouty = Main as ConstraintLayout
            //layouty.setBackgroundColor(Color.BLACK)

            //val linearLayout = findViewById(R.id.Main) as LinearLayout
            //linearLayout.setBackgroundResource(R.drawable.background_fingerboard)


            /*Thread(Runnable {
                Cargando.visibility=View.VISIBLE
                TextCargando.visibility=View.VISIBLE
            }).start()*/


            selectPais()
            /*val boton_vs = Intent(this, MainActivity::class.java)
            boton_vs.putExtra("Pais", nombre)
            startActivity(boton_vs)*/
        }
        creditosButton.setOnClickListener{
            val boton_vs = Intent(this, Main3Activity::class.java)
            startActivity(boton_vs)
            recreate()
        }

        /*creditosButton.setOnClickListener{
            val boton_vs = Intent(this, MainActivity::class.java)
            startActivity(boton_vs)
        }*/
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
    }

    fun selectPais() {
        cargando.visibility=View.VISIBLE
        TextCargando.visibility=View.VISIBLE
        Fondo.visibility=View.INVISIBLE

        playButton.visibility= View.INVISIBLE
        Continuar.visibility= View.INVISIBLE
        Title.visibility=View.INVISIBLE
        creditosButton.visibility= View.INVISIBLE
        //val ran = (Math.random() * 500).toInt()
        //val url="https://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=Paises%20Bajos%20del%20Sur"
        //val url="https://restcountries.eu/rest/v2/all"
        //val url = "https://restcountries.eu/rest/v2/all?fields=translations"
        val url="https://restcountries.eu/rest/v2/all?fields=translations;capital;region;alpha2Code"
        //val url = "https://pokeapi.co/api/v2/pokemon/" + "15"
        getJson(url)

        /*val jsonobj=JSONObject()
        val que= Volley.newRequestQueue(this@MainActivity)
        val req =JsonObjectRequest(Request.Method.GET,url,jsonobj,Response.Listener {response ->
            Toast.makeText(this," Exito...", Toast.LENGTH_LONG).show()

        },Response.ErrorListener {
            Toast.makeText(this," Error",Toast.LENGTH_LONG).show()
        })
        que.add(req)
        var prueba= Prueba as TextView
        prueba.text="Entró a la funcion 'selectPais()'"*/
    }

    fun getJson(url: String) {
        Log.d("depuracion","getJson")

        //an extension over string (support GET, PUT, POST, DELETE with httpGet(), httpPut(), httpPost(), httpDelete())
        url.httpGet().responseJson{ request, response, result ->
            //do something with response
            when (result) {
                is Result.Failure -> {
                    cargando.visibility         =View.INVISIBLE
                    TextCargando.visibility     =View.INVISIBLE

                    venI.visibility             =View.VISIBLE
                    NoInternet.visibility       =View.VISIBLE
                    TextNoInternet.visibility   =View.VISIBLE
                    AceptarNoInternet.visibility=View.VISIBLE

                    //Toast.makeText(this," Error de Conexion", Toast.LENGTH_LONG).show()

                    val boton_vs = Intent(this, MainActivity::class.java)
                    boton_vs.putExtra("Pais", nombre)
                    boton_vs.putExtra("Reiniciar", false)
                    boton_vs.putExtra("conInternet", false)

                    boton_vs.putExtra("Continuar",continuar)

                    AceptarNoInternet.setOnClickListener{
                        startActivity(boton_vs)
                        recreate()
                    }
                }
                is Result.Success -> {
                    val data = result.get()
                    Log.d("data", data.toString())
                    //var prueba= Prueba as TextView

                    Country.Organizar(data)
                    nombre=Country.getName()
                    //

                    ObtenerPaises()

                    //Toast.makeText(this,nombre.toString(), Toast.LENGTH_LONG).show()
                    //val boton_vn = Intent(this, MainActivity::class.java)
                    //startActivity(boton_vn)
                    val boton = Intent(this, MainActivity::class.java)
                    boton.putExtra("Pais", nombre)
                    boton.putExtra("Reiniciar", false)

                    boton.putExtra("conInternet", true)

                    boton.putExtra("Continuar",continuar)

                    boton.putExtra("names",names)
                    boton.putExtra("capitals",capitals)
                    boton.putExtra("regions",regions)
                    boton.putExtra("flags",flags)

                    Handler().postDelayed(Runnable {
                        startActivity(boton)
                        recreate()
                    },2000)
                }
            }
        }
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
}
