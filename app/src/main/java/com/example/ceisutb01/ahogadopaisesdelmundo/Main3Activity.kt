package com.example.ceisutb01.ahogadopaisesdelmundo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        Atras.setOnClickListener{
            val boton_vs = Intent(this, Main2Activity::class.java)
            boton_vs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(boton_vs)
            finish()
        }
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
