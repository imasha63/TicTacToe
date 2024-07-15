package com.example.tictactoe

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat



class GameActivity : AppCompatActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var one: TextView
    private lateinit var two: TextView
    private lateinit var three: TextView
    private lateinit var four: TextView
    private lateinit var five: TextView
    private lateinit var six: TextView
    private lateinit var seven: TextView
    private lateinit var eight: TextView
    private lateinit var nine: TextView

    private lateinit var startNewGameButton: Button
    private lateinit var restartButton : Button
    private lateinit var player1Points: TextView
    private lateinit var player2Points: TextView
    private lateinit var highScoreTextView: TextView // Define highScoreTextView here



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameManager = GameManager()

        // Initialize views
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        startNewGameButton = findViewById(R.id.start_new_game_button)
        restartButton = findViewById(R.id.restart_button)
        player1Points = findViewById(R.id.player_one_score)
        player2Points = findViewById(R.id.player_two_score)
        highScoreTextView = findViewById(R.id.high_score_text) // Initialize highScoreTextView




        // Set click Listeners
        one.setOnClickListener { onBoxClicked(one, Position(  0,  0)) }
        two.setOnClickListener { onBoxClicked(two, Position( 0, 1)) }
        three.setOnClickListener { onBoxClicked(three, Position(0, 2)) }
        four.setOnClickListener { onBoxClicked(four, Position(1, 0)) }
        five.setOnClickListener { onBoxClicked(five, Position(1, 1)) }
        six.setOnClickListener { onBoxClicked(six, Position(1, 2)) }
        seven.setOnClickListener { onBoxClicked(seven, Position(2, 0)) }
        eight.setOnClickListener { onBoxClicked(eight, Position(2,1)) }
        nine.setOnClickListener { onBoxClicked(nine, Position(2, 2)) }

        // Set click listener for the Restart button
        restartButton.setOnClickListener {
            resetGame()
        }

        startNewGameButton.setOnClickListener {
            startNewGameButton.visibility = View.GONE
            gameManager.reset()
            resetboxes()
        }

        updatePoints()
        updateHighScore()



    }

    private fun updatePoints() {
        player1Points.text = "Player X Points: ${gameManager.player1Points}"
        player2Points.text = "Player O Points: ${gameManager.player2Points}"
    }

    private fun resetboxes() {
        val allBoxes = listOf(one, two, three, four, five, six, seven, eight, nine)
        allBoxes.forEach {
            it.text = ""
            it.background = null
            it.isEnabled = true
        }
    }

    private fun onBoxClicked(box: TextView, position: Position) {
        if (box.text.isEmpty()) {
            box.text = gameManager.currentPlayerMark
            val winningLine = gameManager.makeMove(position)
            if (winningLine != null) {
                updatePoints()
                disableBoxes()
                startNewGameButton.visibility = View.VISIBLE
                showWinner(winningLine)
            } else if (isGridFull()){
                restartButton.visibility = View.VISIBLE
            }
        }
    }

    private fun disableBoxes() {
        val allBoxes = listOf(one, two, three, four, five, six, seven, eight, nine)
        allBoxes.forEach {
            it.isEnabled = false
        }
    }

    private fun showWinner(winningLine: WinningLine) {


        // Display winner and update points
        val (winningBoxes, background) = when (winningLine) {
            WinningLine.ROW_0 -> Pair(listOf(one, two, three), R.drawable.horizontal_line)
            WinningLine.ROW_1 -> Pair(listOf(four, five, six), R.drawable.horizontal_line)
            WinningLine.ROW_2 -> Pair(listOf(seven, eight, nine), R.drawable.horizontal_line)
            WinningLine.COLUMN_0 -> Pair(listOf(one, four, seven), R.drawable.vertical_line)
            WinningLine.COLUMN_1 -> Pair(listOf(two, five, eight), R.drawable.vertical_line)
            WinningLine.COLUMN_2 -> Pair(listOf(three, six, nine), R.drawable.vertical_line)
            WinningLine.DIAGONAL_LEFT -> Pair(listOf(one, five, nine),
                R.drawable.left_diagonal_line
            )
            WinningLine.DIAGONAL_RIGHT -> Pair(listOf(three, five, seven),
                R.drawable.right_diagonal_line
            )
        }

        winningBoxes.forEach { box ->
            box.background = ContextCompat.getDrawable(this@GameActivity, background)
        }

        // Update points after save high score
        updatePoints()

        // Update high score
        updateHighScore()

    }



    private fun resetGame(){
        gameManager.reset()
        resetboxes()
        restartButton.visibility = View.GONE
    }

    private fun isGridFull(): Boolean {
        val allBoxes = listOf(one, two, three, four, five, six, seven, eight, nine)
        return allBoxes.none { it.text.isEmpty() }
    }

    // High score
    private fun getHighScore(): Int {
        val sharedPref = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        return sharedPref.getInt("high_score", 0)
    }

    private fun updateHighScore() {
        val maxScore = maxOf(gameManager.player1Points, gameManager.player2Points)
        val sharedPref = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val currentHighScore = sharedPref.getInt("high_score", 0)

        if (maxScore > currentHighScore) {
            val editor = sharedPref.edit()
            editor.putInt("high_score", maxScore)
            editor.apply()
        }

        // Initialize highScoreTextView if it is not already initialized
        if (!::highScoreTextView.isInitialized) {
            highScoreTextView = findViewById(R.id.high_score_text)
        }

        // Update high score value
        highScoreTextView.text = "High Score: ${sharedPref.getInt("high_score", 0)}"
    }





}
