package com.example.ceisutb01.ahogadopaisesdelmundo

import android.os.Bundle
import android.view.View
import java.util.Random
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import com.bumptech.glide.Glide

import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.*
import android.os.Handler
import android.widget.Toast



class  MainActivity : Activity(), View.OnClickListener {

    val letter= arrayOf(("CHAD"),("OMÁN"),("PERÚ"),("NAURU"),("TOGO"),("TONGO"),("TÚNEZ"),("SUIZA"),("SIRIA"),("SAMOA"),("NÍGER"),("NEPAL"),("BENÍN"),("KENIA"),("BUTÁN"),
                        ("ANGOLA"),("ANDORRA"),("ARMENIA"),("AUSTRIA"),("BAHAMAS"),("BARÉIN"),("BÉLGICA"),("CAMERÚN"),("CANADÁ"),("ECUADOR"),("ESPAÑA"),("GAMBIA"),("KOSOVO"),("KUWAIT"),("POLONIA"),
                         ("VENEZUELA"),("VATICANO"),("TANZANIA"),("TAILANDIA"),("SUDÁFRICA"),("SINGAPUR"),("PORTUGAL"),("PALESTINA"),("PAKISTÁN"),("NICARAGUA"),("NAMIBIA"),("MONGOLIA"),("MICRONESIA"),("MARRUECOS"),("MALDIVAS"),
                         ("LIECHTENSTEIN"),("LUXEMBURGO"),("MADAGASCAR"),("MOZAMBIQUE"),("SEYCHELLES"),("TAYIKISTÁN"),("UZBEKISTÁN"),("AFGANISTÁN"),("ARGENTINA"),("BAMGLADESH"),("BIELORRUSIA"),("DINAMARCA"),("ESLOVAQUIA"),("FINLANDIA"),("KIRGUISTÁN"))

    var inicial=0
    var final=14

    var seccion=1

    val random = Random()
    var num = random.nextInt(final - inicial)
    var textviews = ArrayList<TextView>()
    var termino = 0
    var NumVidas = 5
    var Tpuntos = 0
    var auxPuntos = 5
    var Palabra=letter[num]
    var nombre=""

    var conInternet=false

    var names = ArrayList<String>()
    var capitals = ArrayList<String>()
    var regions = ArrayList<String>()
    var flags = ArrayList<String>()

    var numNivel=0

    //val agua= arrayOf(Agua1 as TextView,Agua2 as TextView,Agua3 as TextView,Agua4 as TextView,Agua5 as TextView,Agua5 as TextView,Agua6 as TextView,Agua71 as TextView,Agua72 as TextView,Agua73 as TextView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombre =intent.extras.getString("Pais")

        var prueba= Prueba as TextView

        var reiniciar=intent.extras.getBoolean("Reiniciar")

        var continuar=intent.extras.getBoolean("Continuar")

        conInternet=intent.extras.getBoolean("conInternet")

        if(conInternet){
            //val lista = intent.getSerializableExtra("miLista") as ArrayList<String>
            names= intent.getStringArrayListExtra("names")as ArrayList<String>
            capitals= intent.getStringArrayListExtra("capitals") as ArrayList<String>
            regions= intent.getStringArrayListExtra("regions") as ArrayList<String>
            flags= intent.getStringArrayListExtra("flags") as ArrayList<String>
        }

        if(reiniciar){
            inicial=intent.extras.getInt("Init")
            final=intent.extras.getInt("End")
            Tpuntos=intent.extras.getInt("Score")
            seccion=intent.extras.getInt("Secion")

            //prueba.text=inicial.toString()
            var puntitos = Puntos as TextView
            puntitos.text = "Puntos: " + Tpuntos.toString()
            //prueba.text=Tpuntos.toString()


            guardarPreferencias()

            if(conInternet){
                nombre=SetWordWhitInternet()
            }else{
                while(true) {
                    num = (random.nextInt(final - inicial)) + inicial
                    if(nombre!=letter[num]){
                        break
                    }
                }
                nombre=letter[num]
            }
        }else{
            var puntitos = Puntos as TextView

            if(continuar){
                cargarPreferencias()
            }else{
                guardarPreferencias()
            }

            num = (random.nextInt(final - inicial)) + inicial
            puntitos.text = "Puntos: " + Tpuntos.toString()

            nombre=letter[num]
        }

        numNivel=(((final+1)/15)*2)-3+seccion
        numNivel+=1
        prueba.text="Nivel "+numNivel

        if(!conInternet){
            Palabra=nombre
            //prueba.text=nombre
            init(Palabra.length)
        }else{
            nombre=SetWordWhitInternet()
            nombre=nombre.toUpperCase()
            Palabra=nombre
            //prueba.text=nombre
            init(Palabra.length)
            //prueba.text=nombre

        }
        setBtn()

        Menu2.setOnClickListener {
            val boton_vs = Intent(this, Main2Activity::class.java)
            boton_vs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(boton_vs)
            finish()
        }
        Menu.setOnClickListener {
            val boton_vs = Intent(this, Main2Activity::class.java)
            boton_vs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(boton_vs)
            finish()
        }
        Siguente.setOnClickListener {
            if((numNivel)>=8){
                Siguente.visibility=View.INVISIBLE
                prueba.text="Felicidades, Ganaste el juego"

                GanasteJuego()

                inicial=0
                final=14
                Tpuntos=0
                seccion=1

                guardarPreferencias()
            }else{
                SigORein()
            }

        }

        Reiniciar.setOnClickListener {
            SigORein()
            //finish()
            //m.selectPais()
        }
        Ayuda.setOnClickListener {
            Ayuda.isEnabled = false
            ayudando()
            verificaGano()
        }
    }

    fun GanasteJuego() {
        val respuesta2 = textend2 as TextView
        val respuesta3 = textend3 as TextView
        val respuesta = textend as TextView
        val winL = youwintext as TextView

        Glide.with(this).load(R.mipmap.ic_trophy_gold).into(flag)
        winL.text="¡FELICITACIONES!"
        respuesta.text="¡Eres un GENIO!"
        respuesta2.text="¡Haz aprobado todos los niveles!"
        respuesta3.text="Total puntos: "+Tpuntos

        flag.visibility=View.VISIBLE


    }

    fun SigORein(){
        val boton_vs = Intent(this, MainActivity::class.java)
        boton_vs.putExtra("conInternet", conInternet)
        if(conInternet){
            boton_vs.putExtra("names",names)
            boton_vs.putExtra("capitals",capitals)
            boton_vs.putExtra("regions",regions)
            boton_vs.putExtra("flags",flags)
        }
        boton_vs.putExtra("Pais", Palabra)
        boton_vs.putExtra("Reiniciar", true)
        boton_vs.putExtra("Init", inicial)
        boton_vs.putExtra("End", final)
        boton_vs.putExtra("Score", Tpuntos)
        boton_vs.putExtra("Secion", seccion)
        boton_vs.putExtra("Continuar",false)


        startActivity(boton_vs)
        onDestroy()
        finish()
    }

    fun SetWordWhitInternet():String{
        num=(((final+1)/15)*2)-3+seccion
        return names[num]
    }

    fun ayudando() {
        val ra = Random()
        var VRam = ra.nextInt((Palabra.length - 1) - 0)
        //var VRam=numL[num]-1

        var aux = true
        while (aux) {
            aux = false
            for (i in 0..Palabra.length - 1) {
                //Log.d("TextView",textviews[i].text.toString())
                if (textviews[i].getText().toString() == Palabra[VRam] + " ") {
                    aux = true
                    VRam = ra.nextInt((Palabra.length - 1) - 0)
                    break;
                }

            }
        }
        textviews[VRam].text = Palabra[VRam] + " "
        termino += 1
    }

    fun init(number: Int) {

        val For_Letter = ForLetter as LinearLayout

        for (i in 0..number - 1) {
            val tv_dynamic = TextView(this)
            tv_dynamic.textSize = 25f
            tv_dynamic.text = "__ "
            tv_dynamic.setTypeface(Typeface.SERIF);
            //tv_dynamic.setTextColor(Color.WHITE)
            For_Letter.addView(tv_dynamic)
            textviews.add(tv_dynamic)
        }
        //textviews[0].text=nombre

    }

    override fun onClick(v: View) {
        val b = v as Button
        v.isEnabled = false
        var entro = false
        var conta=0

       // var prueba= Prueba as TextView
        //prueba.text=""

        for (i in Palabra) {

            //prueba.text =prueba.getText().toString() + i.toString()+" "+b.getText().toString()

            if (b.getText().toString() == i.toString() || setToTilde(b.getText().toString())==i.toString()) {
                if (textviews[conta].getText().toString() != b.getText().toString() + " ") {
                    textviews[conta].text = i + " "
                    entro = true
                    termino += 1
                }
            }
            conta+=1
        }
        if (entro == false) {
            verificaPerdio()
            auxPuntos = 5;
        } else {
            var puntitos = Puntos as TextView
            Tpuntos += auxPuntos
            puntitos.text = "Puntos: " + Tpuntos.toString()
            auxPuntos += 5
        }
        verificaGano()
    }

    fun verificaPerdio() {
        var vidas = Vidas as TextView
        NumVidas = NumVidas - 1
        vidas.text = "Vidas: " + NumVidas.toString()

        if(NumVidas==4){
            Agua2.visibility=View.VISIBLE
        }else if(NumVidas==3) {
            Agua2.visibility = View.INVISIBLE
            Agua4.visibility = View.VISIBLE
        }else if(NumVidas==2){
            Agua4.visibility=View.INVISIBLE
            Agua5.visibility=View.VISIBLE
        }else if(NumVidas==1){
            Agua5.visibility=View.INVISIBLE
            Agua6.visibility=View.VISIBLE
        }else if(NumVidas==0){
            Agua6.visibility=View.INVISIBLE
            Agua71.visibility=View.VISIBLE
            Handler().postDelayed(Runnable {
                Agua71.visibility=View.INVISIBLE
                Agua72.visibility=View.VISIBLE
            },100)
            Handler().postDelayed(Runnable {
                val loseL = youlosetext as TextView
                val venI = VenI as ImageView

                venI.visibility = View.VISIBLE
                loseL.visibility = View.VISIBLE
                Buttonoff(false)
            },300)
        }
    }

    fun verificaGano() {
        if (termino == Palabra.length) {
            val winL = youwintext as TextView
            val venI = VenI as ImageView

            venI.visibility = View.VISIBLE
            winL.visibility = View.VISIBLE

            //var prueba= Prueba as TextView
            //prueba.text=(final+1).toString()

            auxPuntos = ((final+1)/15)*(50)
            Tpuntos=Tpuntos+auxPuntos
            var puntitos = Puntos as TextView
            puntitos.text = "Puntos: " + Tpuntos.toString()

            Buttonoff(true)
        }
    }

    fun guardarPreferencias() {
        val prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("puntos",Tpuntos.toString())
        editor.putString("i", inicial.toString())
        editor.putString("f", final.toString())
        editor.putString("s", seccion.toString())
        editor.commit()
        //Toast.makeText(this, "guardando preferencias", Toast.LENGTH_SHORT).show()
    }

    //cargar configuración aplicación Android usando SharedPreferences
    fun cargarPreferencias() {
        val prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        inicial = prefs.getString("i", "0").toInt()
        final = prefs.getString("f","14").toInt()
        Tpuntos=prefs.getString("puntos","0").toInt()
        seccion=prefs.getString("s","1").toInt()
       // Toast.makeText(this, preferencias1, Toast.LENGTH_SHORT).show()
    }

    fun Buttonoff(gano: Boolean) {
        val menu = Menu as Button
        val ayuda = Ayuda as Button
        val For_Letterabc = Vocales as LinearLayout
        val For_Letterabc2 = LetrasABC as LinearLayout


        val respuesta2= textend2 as TextView
        val respuesta3= textend3 as TextView
        val respuesta= textend as TextView


        val menu2 = Menu2 as Button
        val sigieunte = Siguente as Button
        val reiniciar = Reiniciar as Button
        val Flag = flag as ImageView

        //var prueba= Prueba as TextView

        menu.visibility = View.INVISIBLE
        ayuda.visibility = View.INVISIBLE

        menu2.visibility = View.VISIBLE
        if(gano){
            respuesta.text="Exacto, la respuesta es "+Palabra
            sigieunte.visibility = View.VISIBLE

            if(numNivel<8) {
                if (seccion == 2) {
                    inicial = final + 1
                    final = inicial + 14
                    seccion = 1
                } else {
                    seccion = 2
                }
            }
            
        }else{
            respuesta.text="La respuesta es "+Palabra
            if(!conInternet) {
                reiniciar.visibility = View.VISIBLE
            }

            inicial=0
            final=14
            Tpuntos=0
            seccion=1

        }

        if(conInternet){

            respuesta2.text="Capital: "+capitals[num]
            respuesta3.text="Region: "+regions[num]

            //prueba.text=flags[num]
            var url="http://flagpedia.net/data/flags/w580/"+flags[num].toLowerCase()+".png"
            Glide.with(this).load(url).into(flag)

            Flag.visibility=View.VISIBLE
        }else{
            if(gano) {
                respuesta2.text = "¡GENIAL!"
                respuesta3.text = "¡Continua al siguiente nivel!"
            }else{
                respuesta2.text = "¡Que mal!"
                respuesta3.text = "¡Haz perdido todas tus vidas!"

            }
        }
        respuesta2.visibility=View.VISIBLE
        respuesta3.visibility=View.VISIBLE

        For_Letterabc.visibility = View.INVISIBLE
        For_Letterabc2.visibility = View.INVISIBLE


        respuesta.visibility=View.VISIBLE

        guardarPreferencias()
    }

    fun setBtn() {
        buttonA.setOnClickListener(this)
        buttonB.setOnClickListener(this)
        buttonC.setOnClickListener(this)
        buttonD.setOnClickListener(this)
        buttonE.setOnClickListener(this)
        buttonF.setOnClickListener(this)
        buttonG.setOnClickListener(this)
        buttonH.setOnClickListener(this)
        buttonI.setOnClickListener(this)
        buttonJ.setOnClickListener(this)
        buttonK.setOnClickListener(this)
        buttonL.setOnClickListener(this)
        buttonM.setOnClickListener(this)
        buttonN.setOnClickListener(this)
        buttonNI.setOnClickListener(this)
        buttonO.setOnClickListener(this)
        buttonP.setOnClickListener(this)
        buttonQ.setOnClickListener(this)
        buttonR.setOnClickListener(this)
        buttonS.setOnClickListener(this)
        buttonT.setOnClickListener(this)
        buttonU.setOnClickListener(this)
        buttonV.setOnClickListener(this)
        buttonW.setOnClickListener(this)
        buttonX.setOnClickListener(this)
        buttonY.setOnClickListener(this)
        buttonZ.setOnClickListener(this)
    }

    fun setToTilde(l:String):String{
        val abecedario: HashMap<String, String> = hashMapOf("A" to "Á", "B" to "B",
                "C" to "C", "D" to "D", "E" to "É", "F" to "F", "G" to "G", "H" to "H",
                "I" to "Í", "J" to "J", "K" to "K", "L" to "L", "M" to "M", "N" to "N",
                "Ñ" to "Ñ", "O" to "Ó", "P" to "P", "Q" to "Q", "R" to "R","S" to "S",
                "T" to "T", "U" to "Ú", "V" to "V", "W" to "W", "X" to "X","Y" to "Y",
                "Z" to "Z")
        return abecedario[l].toString()

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
}
