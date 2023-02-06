package com.example.pigdice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val RESET_SCORE_ON_ONES = true

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class Dice(var numSides: Int) {
    fun roll(): Int {
        return if (RESET_SCORE_ON_ONES) {
            (1..numSides).random()
        } else {
            (2..numSides).random()
        }
    }
}