package com.example.pigdice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

const val RESET_SCORE_ON_ONES = true

class MainActivity : AppCompatActivity() {

    //You Variables
    lateinit var tvYou: TextView
    private lateinit var tvYouGamesWon: TextView
    private lateinit var tvYouTotalScore: TextView
    private lateinit var tvYouTurnTotal: TextView

    //Computer Variables
    lateinit var tvComputer: TextView
    private lateinit var tvComputerGamesWon: TextView
    lateinit var tvComputerTotalScore: TextView
    private lateinit var tvComputerTurnTotal: TextView

    //Dice Variables
    private lateinit var tvScore: TextView
    private lateinit var ivDice1: ImageView
    private lateinit var ivDice2: ImageView

    //Button Variables
    lateinit var btnRoll: Button
    lateinit var btnHold: Button

    private lateinit var ivWinLose: ImageView

    var dice1 = Dice(6)
    var dice2 = Dice(6)

    private var playerTotalScore = 0
    private var playerTurnScore = 0
    private var playerGamesWon = 0

    var compTotalScore = 0
    var compTurnScore = 0
    private var compGamesWon = 0

    private var diceTotal = 0
    var playerTurn = true
    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initApplication()
    }

    private fun initApplication() {
        tvYou = findViewById(R.id.tvYou)
        tvYou.setBackgroundColor(Color.LTGRAY)
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

        ivWinLose = findViewById(R.id.ivWinLose)
        ivWinLose.visibility = View.GONE
    }

    fun onRollCLick(v: View) {
        val dice1Roll = dice1.roll()
        val dice2Roll = dice2.roll()

        changeDiceDrawable(dice1Roll, dice2Roll)
        checkForOnesPlayer(dice1Roll, dice2Roll)

        if (!playerTurn) {
            object : CountDownTimer(2000, 1000) {
                override fun onTick(p0: Long) {
                    //place holder to see change from player turn to computer
                }

                override fun onFinish() {
                    onHold()
                }
            }.start()
            tvYouTotalScore.text = "$playerTotalScore"
            playerTotalScore += playerTurnScore
            playerTurnScore = 0
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
        btnHold.isEnabled = false
        btnRoll.isEnabled = false
        tvYou.setBackgroundColor(Color.WHITE)
        tvComputer.setBackgroundColor(Color.LTGRAY)
        object : CountDownTimer(6000, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!gameOver) {
                    if (!playerTurn) {
                        val dice1Roll = dice1.roll()
                        val dice2Roll = dice2.roll()

                        changeDiceDrawable(dice1Roll, dice2Roll)

                        checkForOnesComp(dice1Roll, dice2Roll)
                    }
                }
            }

            override fun onFinish() {
                compTotalScore += compTurnScore
                tvComputerTotalScore.text = "$compTotalScore"
                compTurnScore = 0
                playerTurn = true
                btnHold.isEnabled = true
                btnRoll.isEnabled = true
                tvYou.setBackgroundColor(Color.LTGRAY)
                tvComputer.setBackgroundColor(Color.WHITE)
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

    private fun isWinner() {
        if (playerTotalScore + playerTurnScore >= 100) {
            playerGamesWon += 1
            tvYouGamesWon.text = "$playerGamesWon"
            ivWinLose.setImageResource(R.drawable.win)
            ivWinLose.visibility = View.VISIBLE
            gameOver = true
            btnHold.isEnabled = false
            btnRoll.isEnabled = false
        } else if (compTotalScore + compTurnScore >= 100) {
            compGamesWon += 1
            tvComputerGamesWon.text = "$compGamesWon"
            ivWinLose.setImageResource(R.drawable.lose)
            ivWinLose.visibility = View.VISIBLE
            gameOver = true
            btnHold.isEnabled = false
            btnRoll.isEnabled = false
        }
    }

    fun newRound(v: View) {
        playerTotalScore = 0
        tvYouTotalScore.text = "$playerTotalScore"

        playerTurnScore = 0
        tvYouTurnTotal.text = "$playerTurnScore"

        compTotalScore = 0
        tvComputerTotalScore.text = "$compTotalScore"

        compTurnScore = 0
        tvComputerTurnTotal.text = "$compTurnScore"

        ivWinLose.visibility = View.GONE

        btnHold.isEnabled = true
        btnRoll.isEnabled = true

        playerTurn = true
        gameOver = false
    }

    fun checkForOnesComp(dice1Roll: Int, dice2Roll: Int) {
        if (dice1Roll != 1 && dice2Roll != 1) {
            diceTotal = dice1Roll + dice2Roll
            tvScore.text = "$diceTotal"
            compTurnScore += diceTotal
            tvComputerTurnTotal.text = "$compTurnScore"
            isWinner()
            if (gameOver) {
                gameOver = false
            }
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

    private fun checkForOnesPlayer(dice1Roll: Int, dice2Roll: Int) {
        if (dice1Roll != 1 && dice2Roll != 1) {
            diceTotal = dice1Roll + dice2Roll
            tvScore.text = "$diceTotal"
            playerTurnScore += diceTotal
            tvYouTurnTotal.text = "$playerTurnScore"
            isWinner()
            if (gameOver) {
                gameOver = false
            }
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
    }
}

class Dice(private var numSides: Int) {
    fun roll(): Int {
        return if (RESET_SCORE_ON_ONES) {
            (1..numSides).random()
        } else {
            (2..numSides).random()
        }
    }
}


