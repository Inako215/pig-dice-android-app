package com.example.pigdice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

const val RESET_SCORE_ON_ONES = true

class MainActivity : AppCompatActivity() {

    //You Variables
    lateinit var tvYou : TextView
    lateinit var tvYouGamesWon : TextView
    lateinit var tvYouTotalScore : TextView
    lateinit var tvYouTurnTotal : TextView

    //Computer Variables
    lateinit var tvComputer : TextView
    lateinit var tvComputerGamesWon : TextView
    lateinit var tvComputerTotalScore : TextView
    lateinit var tvComputerTurnTotal : TextView

    //Dice Variables
    lateinit var tvScore : TextView
    lateinit var ivDice1 : ImageView
    lateinit var ivDice2 : ImageView

    //Button Variables
    lateinit var btnRoll : Button
    lateinit var btnHold : Button

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