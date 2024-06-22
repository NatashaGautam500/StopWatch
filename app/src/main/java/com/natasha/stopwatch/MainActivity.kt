package com.natasha.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    var running = false
    var wasRunning = false
    var seconds = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
        runTimer()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    public override fun onSaveInstanceState(
        savedInstanceState: Bundle
    ){
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("seconds", seconds)
        savedInstanceState.putBoolean("running", running)
        savedInstanceState.putBoolean("wasRunning", wasRunning)
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if(wasRunning){
            running = true
        }
    }

    private fun runTimer() {
        val myTv = findViewById<TextView>(R.id.tv) as TextView
        val handler = Handler()
        handler.post(object : Runnable{
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
                myTv.text = time
                if(running){
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    fun ResetBtn(view: View) {
        running = false
        seconds = 0
    }
    fun StopBtn(view: View) {
        running = false
    }
    fun StartBtn(view: View) {
        running = true
    }
}