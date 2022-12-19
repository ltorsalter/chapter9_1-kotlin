package com.example.lab6_kotlin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var progresssRabbit = 0
    private var progresssTurtle = 0
    private lateinit var btn_start:Button
    private lateinit var sb_rabbit:SeekBar
    private lateinit var sb_turtle:SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start = findViewById(R.id.btn_start)
        sb_rabbit = findViewById(R.id.sb_rabbit)
        sb_turtle = findViewById(R.id.sb_turtle)
        btn_start.setOnClickListener(View.OnClickListener {
            btn_start.isEnabled = false
            progresssRabbit = 0
            progresssTurtle = 0
            sb_rabbit.progress = 0
            sb_turtle.progress = 0
            runRabbit()
            runTurtle()
        })
    }

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1) sb_rabbit.progress = progresssRabbit
        else if (msg.what == 2) sb_turtle!!.progress = progresssTurtle

        if (progresssRabbit >= 100 && progresssTurtle < 100) {
            Toast.makeText(this, "兔子勝利", Toast.LENGTH_LONG).show()
            btn_start!!.isEnabled = true
        } else if (progresssTurtle >= 100 && progresssRabbit < 100) {
            Toast.makeText(this, "烏龜勝利", Toast.LENGTH_LONG).show()
            btn_start!!.isEnabled = true
        }
        true
    }

    private fun runRabbit() {
        Thread {
            val sleepProbability = booleanArrayOf(true, true, false)
            while (progresssRabbit <= 100 && progresssTurtle < 100) {
                try {
                    Thread.sleep(100)
                    if (sleepProbability[(Math.random() * 3).toInt()]) Thread.sleep(
                        300
                    )
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progresssRabbit += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle() {
        Thread {
            while (progresssTurtle <= 100 && progresssRabbit < 100) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progresssTurtle += 1
                val msg = Message()
                msg.what = 2
                handler.sendMessage(msg)
            }
        }.start()
    }
}