package com.pf.conometro

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var segundos =0
    private var running = false
    private var wasRunning = false

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        if (savedInstanceState != null){
            segundos = savedInstanceState.getInt("segundos")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }

        runTimer()
    }

    fun onClickStart(view: View) {
        running = true
        val stopButton: Button = findViewById(R.id.stop_button)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        stopButton.visibility = View.VISIBLE
        stopButton.startAnimation(fadeIn)
    }


    fun onClickStop(view: View) {
        running = false
    }


    fun onClickReset(view: View) {
        running = false
        segundos = 0
        val stopButton: Button = findViewById(R.id.stop_button)
        stopButton.visibility = View.GONE
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        stopButton.startAnimation(fadeIn)
    }

    private fun runTimer(){
        val  timeView: TextView = findViewById(R.id.time_view)
        handler.post(object : Runnable{
            override fun run() {
                val horas = segundos/3600
                val minutos = (segundos % 3600)/60
                val secs = segundos % 60

                timeView.text = String.format("%02d:%02d:%02d", horas, minutos, secs)
                if (running){
                    segundos++
                }
                handler.postDelayed(this,1000)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("segundos", segundos)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }
}