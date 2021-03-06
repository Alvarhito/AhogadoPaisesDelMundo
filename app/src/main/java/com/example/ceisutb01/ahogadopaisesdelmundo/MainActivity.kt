package com.example.ceisutb01.ahogadopaisesdelmundo

import android.annotation.SuppressLint
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
import android.graphics.*
import android.os.Handler
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main4.*
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.reward.RewardedVideoAd
import android.widget.Toast
import com.google.android.gms.ads.reward.RewardItem
import android.view.animation.AnimationUtils
import android.view.animation.Animation





class MainActivity : Activity(), View.OnClickListener {


    var ref = FirebaseDatabase.getInstance().reference
    var Espera=ref

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

    var jugadorVs=""
    var jugador=""
    var sala=""

    var l = ArrayList<Int>()
    var lVs = ArrayList<Int>()

    var ir=false

    var puntosEnemigo=""
    var ganar=true
    var otroJugador=false

    //val agua= arrayOf(Agua1 as TextView,Agua2 as TextView,Agua3 as TextView,Agua4 as TextView,Agua5 as TextView,Agua5 as TextView,Agua6 as TextView,Agua71 as TextView,Agua72 as TextView,Agua73 as TextView)

    lateinit var mAdView : AdView
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    //private val mSmallBang: SmallBang? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this,"ca-app-pub-9105229171557561~6203725828")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)

        /*
        val mAdView = adView as AdView
        val adRequest = AdRequest.Builder()
                .build()
        mAdView.loadAd(adRequest)*/



        nombre =intent.extras.getString("Pais")

        var prueba= Prueba as TextView

        var reiniciar=intent.extras.getBoolean("Reiniciar")

        var continuar=intent.extras.getBoolean("Continuar")

        conInternet=intent.extras.getBoolean("conInternet")

        if(conInternet){
            sala=intent.extras.getString("sala", "0")
            jugador=intent.extras.getString("player","0")
            jugadorVs=intent.extras.getString("playerVs","0")

            if(sala!="0"){
                ref.child("Servicio").child(sala).child("Esperar").child("p1").setValue("0")
                ref.child("Servicio").child(sala).child("Esperar").child("p2").setValue("0")

                ref.child("Servicio").child(sala).child(jugadorVs).addValueEventListener(object : ValueEventListener {

                    @SuppressLint("ResourceAsColor")
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val t = object : GenericTypeIndicator<ArrayList<Int>>() {}
                        val value = dataSnapshot.getValue(t)
                        lVs=value!!

                        val paraEspacio=ForSpace as LinearLayout

                        val For_Letter = ForLetter as LinearLayout
                        var con=0
                        for(i in 0..lVs.size-1){
                            if(lVs[i]==1){
                                con+=1
                                if(For_Letter.getChildAt(i)!=null) {
                                    For_Letter.getChildAt(i).setBackgroundColor(Color.BLUE)
                                }
                                //For_Letter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
                                //paraEspacio.getChildAt(i).visibility=View.VISIBLE
                            }
                        }

                        if(con==Palabra.length){
                            NumVidas=1
                            if(ganar){
                                otroJugador=true
                                verificaPerdio()
                            }else{
                                val respuesta2= textend2 as TextView
                                val respuesta3= textend3 as TextView
                                val respuesta= textend as TextView

                                respuesta.text = "PUNTOS"
                                respuesta2.text = "TU: " + Tpuntos
                                respuesta3.text = "OPONENTE: " + puntosEnemigo
                                Siguente.visibility=View.VISIBLE
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        val prueba= prueba as TextView
                        prueba.text="Error"
                    }
                })
            }

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

            if(sala=="0") {
                guardarPreferencias()
            }

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
                if(sala=="0"){
                    guardarPreferencias()
                }
            }

            num = (random.nextInt(final - inicial)) + inicial
            puntitos.text = "Puntos: " + Tpuntos.toString()

            nombre=letter[num]
        }



        numNivel=(((final+1)/15)*2)-3+seccion
        numNivel+=1
        prueba.text="Nivel "+numNivel

        if(numNivel==1){
            Instruc.visibility=View.VISIBLE
            Instruc.setOnClickListener{
                Instruc.visibility=View.INVISIBLE
            }
        }

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
                Siguente.visibility = View.INVISIBLE
                if(sala=="0") {
                    prueba.text = "Felicidades, Ganaste el juego"
                    GanasteJuego()
                }else{
                    val respuesta2= textend2 as TextView
                    val respuesta3= textend3 as TextView
                    val respuesta= textend as TextView
                    val p = youlosetext as TextView
                    val g= youwintext as TextView


                    if(Tpuntos > puntosEnemigo.toInt()){
                        Glide.with(this).load(R.mipmap.ic_trophy_gold).into(flag)
                        p.visibility=View.INVISIBLE
                        g.visibility=View.VISIBLE
                        flag.visibility=View.VISIBLE

                    }else if(Tpuntos < puntosEnemigo.toInt()){
                        g.visibility=View.INVISIBLE
                        p.visibility=View.VISIBLE
                    }else{
                        p.text="¡EMPATE!"
                        g.visibility=View.INVISIBLE
                        p.visibility=View.VISIBLE
                    }
                    respuesta.text = "FIN DEL JUEGO"
                    respuesta2.text = "TUS PUNTOS: " + Tpuntos
                    respuesta3.text = "PUNTOS OPONENTE: " + puntosEnemigo
                }

                inicial=0
                final=14
                Tpuntos=0
                seccion=1

                if(sala=="0") {
                    guardarPreferencias()
                }
            }else{
                if(sala=="0" || conInternet==false){
                    SigORein()
                }else{
                    val respuesta2= textend2 as TextView
                    val respuesta3= textend3 as TextView
                    val respuesta= textend as TextView

                    flag.visibility=View.INVISIBLE
                    if(otroJugador) {
                        respuesta.text = "PUNTOS"
                        respuesta2.text = "TU: " + Tpuntos
                        respuesta3.text = "OPONENTE: " + puntosEnemigo
                    }else{
                        respuesta.visibility=View.INVISIBLE
                        respuesta2.text="Esperando oponente..."
                        respuesta3.visibility=View.INVISIBLE
                        Siguente.visibility=View.INVISIBLE
                        if (jugador == "player1") {
                            ref.child("Servicio").child(sala).child("Esperar").child("p1").setValue("1")
                        }else if(jugador == "player2"){
                            ref.child("Servicio").child(sala).child("Esperar").child("p2").setValue("1")
                        }
                        ir=true
                    }

                    Siguente.setOnClickListener {
                        respuesta.visibility=View.INVISIBLE
                        respuesta2.text="Esperando oponente..."
                        respuesta3.visibility=View.INVISIBLE

                        if (jugador == "player1") {
                            ref.child("Servicio").child(sala).child("Esperar").child("p1").setValue("1")
                        }else if(jugador == "player2"){
                            ref.child("Servicio").child(sala).child("Esperar").child("p2").setValue("1")
                        }
                        ir=true

                    }
                }
            }

        }

        Reiniciar.setOnClickListener {
            SigORein()
            //finish()
            //m.selectPais()
        }

        Ayuda.setOnClickListener {
            //loadRewardedVideoAd()
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
            if(sala!="0"){
                var r= arrayListOf<Int>(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
                ref.child("Servicio").child(sala).child(jugadorVs).setValue(r)
                boton_vs.putExtra("player",jugador)
                boton_vs.putExtra("sala",sala)
                boton_vs.putExtra("playerVs",jugadorVs)
            }

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
        finish()
        onDestroy()
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
                if ((textviews[i].getText().toString() == Palabra[VRam] + " ") || (textviews[i].getText().toString() == " "+Palabra[VRam] + " ") ) {
                    aux = true
                    VRam = ra.nextInt((Palabra.length - 1) - 0)
                    break;
                }

            }
        }
        textviews[VRam].text = Palabra[VRam] + " "

        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.abc_fade_out)
        textviews[VRam].startAnimation(animation)

        l[VRam]=1
        if(sala!="0") {
            ref.child("Servicio").child(sala).child(jugador).setValue(l)
        }

        termino += 1
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                AdRequest.Builder().build())
        if (mRewardedVideoAd.isLoaded) {
            mRewardedVideoAd.show()
        }
    }

    fun onRewarded(reward: RewardItem) {
        Toast.makeText(this, "onRewarded! currency: " + reward.type + "  amount: " +
                reward.amount, Toast.LENGTH_SHORT).show()
        // Reward the user.
    }

    fun init(number: Int) {

        val For_Letter = ForLetter as LinearLayout
        val paraEspacio=ForSpace as LinearLayout


        for (i in 0..(15 - number)-1){
            paraEspacio.removeView(paraEspacio.getChildAt(0
            ))
        }

        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.marginEnd=3
        for (i in 0..number - 1) {
            //paraEspacio.getChildAt(i).visibility=View.VISIBLE
            val tv_dynamic = TextView(this)
            tv_dynamic.setLayoutParams(params)
            tv_dynamic.textSize = 25f
            tv_dynamic.text = "__"
            tv_dynamic.setTypeface(Typeface.SERIF);
            //tv_dynamic.setTextColor(Color.WHITE)
            For_Letter.addView(tv_dynamic)
            textviews.add(tv_dynamic)
            l.add(0)
        }
        l.add(0)
        l.add(0)
        l.add(0)
        //if(sala!="0") {
        //    ref.child("Servicio").child(sala).child(jugador).setValue(l)
        //}
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
                    l[conta]=1
                    if(i=='I'){
                        textviews[conta].text = " "+ i + " "
                    }else{
                        textviews[conta].text = i + " "

                        /*
                        mSmallBang.bang(textviews[conta], 50, object : SmallBangListener() {
                            fun onAnimationStart() {}
                            fun onAnimationEnd() {
                            }
                        })*/
                    }
                    val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.abc_fade_out)
                    textviews[conta].startAnimation(animation)
                    entro = true
                    termino += 1
                }
            }
            conta+=1
        }
        if(sala!="0") {
            ref.child("Servicio").child(sala).child(jugador).setValue(l)
        }

        if (entro == false) {
            verificaPerdio()
            auxPuntos = 5;
        } else {
            var puntitos = Puntos as TextView
            Tpuntos += auxPuntos
            puntitos.text = "Puntos: " + Tpuntos.toString()

            val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.abc_fade_out)
            puntitos.startAnimation(animation)

            auxPuntos += 5
        }
        verificaGano()
    }

    fun verificaPerdio() {
        var vidas = Vidas as TextView
        NumVidas = NumVidas - 1
        vidas.text = "Intentos: " + NumVidas.toString()
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.abc_fade_out)
        vidas.startAnimation(animation)


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
        Tpuntos=prefs.getString("puntos","0 ").toInt()
        seccion=prefs.getString("s","1").toInt()
       // Toast.makeText(this, preferencias1, Toast.LENGTH_SHORT).show()
    }

    fun Buttonoff(gano: Boolean) {
        ganar=gano

        if(sala!="0") {
            if (jugador == "player1") {
                ref.child("Servicio").child(sala).child("Puntos").child("puntos1").setValue(Tpuntos.toString())
            } else if (jugador == "player2") {
                ref.child("Servicio").child(sala).child("Puntos").child("puntos2").setValue(Tpuntos.toString())
            }
        }

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
            otroJugador=true
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
            if(sala!="0") {
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
                inicial=0
                final=14
                Tpuntos=0
                seccion=1
            }
            respuesta.text="La respuesta es "+Palabra
            if(!conInternet) {
                reiniciar.visibility = View.VISIBLE
            }

        }

        if(conInternet){

            respuesta2.text="Capital: "+capitals[num]
            respuesta3.text="Region: "+regions[num]

            //prueba.text=flags[num]
            var url="http://flagpedia.net/data/flags/w580/"+flags[num].toLowerCase()+".png"
            if(sala=="0") {
                Glide.with(this).load(url).into(flag)
            }

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
        if(sala=="0") {
            guardarPreferencias()
        }
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
        val uiOptions = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions

        Espera.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var value=dataSnapshot.child("Servicio").child(sala).child("Esperar").child("p2").getValue(String::class.java)
                if (jugador == "player1") {
                    value=dataSnapshot.child("Servicio").child(sala).child("Puntos").child("puntos2").getValue(String::class.java)
                    puntosEnemigo=value!!
                }else if (jugador == "player2") {
                    value=dataSnapshot.child("Servicio").child(sala).child("Puntos").child("puntos1").getValue(String::class.java)
                    puntosEnemigo=value!!
                }

                if(ir==true) {
                    //val value = dataSnapshot.getValue(String::class.java)
                    //val prueba= prueba as TextView
                    //

                    //prueba.text="Estoy accediendo a: "+

                    if (jugador == "player1") {
                        value=dataSnapshot.child("Servicio").child(sala).child("Esperar").child("p2").getValue(String::class.java)
                        if(value=="1") {
                            if(ir) {
                                Handler().postDelayed(Runnable {
                                    SigORein()
                                    ir=false
                                }, 1000)
                            }
                        }
                    }else if (jugador == "player2") {
                        value=dataSnapshot.child("Servicio").child(sala).child("Esperar").child("p1").getValue(String::class.java)
                        if(value=="1"){
                            if(ir) {
                                Handler().postDelayed(Runnable {
                                    SigORein()
                                    ir=false
                                }, 1000)
                            }
                            //Cliente.isEnabled = true
                            //Cliente.setTextColor(Color.WHITE)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                val prueba= prueba as TextView
                prueba.text="Error"
            }
        })
    }
}
