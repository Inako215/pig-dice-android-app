package com.example.pigdice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

const val RESET_SCORE_ON_ONES = true

class MainActivity : AppCompatActivity() {

    //You Variables
    lateinit var tvYou: TextView
    lateinit var tvYouGamesWon: TextView
    lateinit var tvYouTotalScore: TextView
    lateinit var tvYouTurnTotal: TextView

    //Computer Variables
    lateinit var tvComputer: TextView
    lateinit var tvComputerGamesWon: TextView
    lateinit var tvComputerTotalScore: TextView
    lateinit var tvComputerTurnTotal: TextView

    //Dice Variables
    lateinit var tvScore: TextView
    lateinit var ivDice1: ImageView
    lateinit var ivDice2: ImageView

    //Button Variables
    lateinit var btnRoll: Button
    lateinit var btnHold: Button

    var dice1 = Dice(6)
    var dice2 = Dice(6)

    var playerTotalScore = 0
    var playerTurnScore = 0
    var playerGamesWon = 0

    var compTotalScore = 0
    var compTurnScore = 0
    var compGamesWon = 0

    var diceTotal = 0
    var playerTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initApplication()
    }

    private fun initApplication() {
        tvYou = findViewById(R.id.tvYou)
        tvYouGamesWon = findViewById(R.id.tvYouGamesWon)
        tvYouTotalScore = findViewById(R.id.tvYouTotalScore)
        tvYouTurnTotal = findViewById(R.id.tvYouTurnTotal)

        tvComputer = findViewById(R.id.tvComputer)
        tvComputerGamesWon = findViewById(R.id.tvComputerGamesWon)
        tvComputerTotalScore = findViewById(R.id.tvComputerTotalScore)
        tvComputerTurnTotal = findViewById(R.id.tvComputerTurnTotal)

        tvScore = findViewById(R.id.tvScore)
        ivDice1 = findViewById(R.id.ivDice1)
        ivDice2 = findViewById(R.id.ivDice2)

        btnRoll = findViewById(R.id.btnRoll)
        btnHold = findViewById(R.id.btnHold)
    }

    fun onRollCLick(v: View) {
        var dice1Roll = dice1.roll()
        var dice2Roll = dice2.roll()
        Log.i("compTurnDelay", "Delaying comp turn")


        changeDiceDrawable(dice1Roll, dice2Roll)

        if (dice1Roll != 1 && dice2Roll != 1) {
            diceTotal = dice1Roll + dice2Roll
            tvScore.text = "$diceTotal"
            playerTurnScore += diceTotal
            tvYouTurnTotal.text = "$playerTurnScore"
        } else {
            if (dice1Roll == 1 && dice2Roll == 1) {
                playerTurnScore = 0
                playerTotalScore = 0
                tvYouTurnTotal.text = "$playerTurnScore"
                tvYouTotalScore.text = "$playerTotalScore"
            } else {
                playerTurnScore = 0
                tvYouTurnTotal.text = "$playerTurnScore"
            }
            playerTurn = false
        }
        if (!playerTurn) {
            object : CountDownTimer(2000, 1000) {
                override fun onTick(p0: Long) {
                    Log.i("compTurnDelay", "Delaying comp turn")
                }

                override fun onFinish() {
                    Log.i("compTurnDelay", "onFinish")
                    tvYouTotalScore.text = "$playerTotalScore"
                    playerTotalScore += playerTurnScore
                    playerTurnScore = 0
                    onHold()
                }
            }.start()
        }
    }

    fun onHoldClick(v: View) {
        playerTurn = false
        playerTotalScore += playerTurnScore
        tvYouTotalScore.text = "$playerTotalScore"
        playerTurnScore = 0
        onHold()
    }

    fun onHold() {
        object : CountDownTimer(6000, 2000) {
            override fun onTick(millisUntilFinished: Long) {

                if (!playerTurn) {

                    var dice1Roll = dice1.roll()
                    var dice2Roll = dice2.roll()

                    changeDiceDrawable(dice1Roll, dice2Roll)

                    if (dice1Roll != 1 && dice2Roll != 1) {
                        diceTotal = dice1Roll + dice2Roll
                        tvScore.text = "$diceTotal"
                        compTurnScore += diceTotal
                        tvComputerTurnTotal.text = "$compTurnScore"
                    } else {
                        if (dice1Roll == 1 && dice2Roll == 1) {
                            compTurnScore = 0
                            compTotalScore = 0
                            tvComputerTurnTotal.text = "$compTurnScore"
                            tvComputerTotalScore.text = "$compTotalScore"
                        } else {
                            compTurnScore = 0
                            tvComputerTurnTotal.text = "$compTurnScore"
                        }
                        playerTurn = true
                    }
                }
                Log.i("compTurnDelay", "compRolling")
            }

            override fun onFinish() {
                compTotalScore += compTurnScore
                tvComputerTotalScore.text = "$compTotalScore"
                compTurnScore = 0
                playerTurn = true
            }
        }.start()
    }

    fun changeDiceDrawable(dice1Roll: Int, dice2Roll: Int) {
        when (dice1Roll) {
            1 -> ivDice1.setImageResource(R.drawable.dice_1)
            2 -> ivDice1.setImageResource(R.drawable.dice_2)
            3 -> ivDice1.setImageResource(R.drawable.dice_3)
            4 -> ivDice1.setImageResource(R.drawable.dice_4)
            5 -> ivDice1.setImageResource(R.drawable.dice_5)
            6 -> ivDice1.setImageResource(R.drawable.dice_6)
        }

        when (dice2Roll) {
            1 -> ivDice2.setImageResource(R.drawable.dice_1)
            2 -> ivDice2.setImageResource(R.drawable.dice_2)
            3 -> ivDice2.setImageResource(R.drawable.dice_3)
            4 -> ivDice2.setImageResource(R.drawable.dice_4)
            5 -> ivDice2.setImageResource(R.drawable.dice_5)
            6 -> ivDice2.setImageResource(R.drawable.dice_6)
        }
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


