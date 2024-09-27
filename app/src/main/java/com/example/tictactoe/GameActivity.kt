package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    private var player1Symbol: String = ""
    private var player2Symbol: String = ""
    private var isPlayer1Turn: Boolean = true
    private var gameBoard = Array(3) { IntArray(3) }

    private var mode: String? = null // To store if it's AI or Friend mode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        mode = intent.getStringExtra("mode")
        val chosenSymbol = intent.getStringExtra("symbol")
        val player1Name = if (mode == "AI") "Player" else "Player 1"
        val player2Name = if (mode == "AI") "AI" else "Player 2"

        // Assign symbols based on the choice
        player1Symbol = if (chosenSymbol == "X") "X" else "O"
        player2Symbol = if (chosenSymbol == "X") "O" else "X"

        // Update UI to show players' names
        val tvPlayer1 = findViewById<TextView>(R.id.tvPlayer1Name)
        val tvPlayer2 = findViewById<TextView>(R.id.tvPlayer2Name)
        tvPlayer1.text = "$player1Name ($player1Symbol)"
        tvPlayer2.text = "$player2Name ($player2Symbol)"

        // Initialize game board (ImageViews for each cell)
        setupGameBoard()
    }

    private fun setupGameBoard() {
        val gridTiles = listOf(
            findViewById<ImageView>(R.id.iv1),
            findViewById<ImageView>(R.id.iv2),
            findViewById<ImageView>(R.id.iv3),
            findViewById<ImageView>(R.id.iv4),
            findViewById<ImageView>(R.id.iv5),
            findViewById<ImageView>(R.id.iv6),
            findViewById<ImageView>(R.id.iv7),
            findViewById<ImageView>(R.id.iv8),
            findViewById<ImageView>(R.id.iv9)
        )

        // Add click listeners to all grid tiles
        for ((index, tile) in gridTiles.withIndex()) {
            tile.setOnClickListener {
                if (isPlayer1Turn || mode == "Friend") {
                    handleTileClick(tile, index / 3, index % 3)
                }
            }
        }
    }

    private fun handleTileClick(tile: ImageView, row: Int, col: Int) {
        if (gameBoard[row][col] != 0) return // Ignore if the cell is already taken

        if (isPlayer1Turn) {
            tile.setImageResource(if (player1Symbol == "X") R.drawable.symbol_x else R.drawable.symbol_o)
            gameBoard[row][col] = 1
        } else {
            tile.setImageResource(if (player2Symbol == "X") R.drawable.symbol_x else R.drawable.symbol_o)
            gameBoard[row][col] = 2
        }

        if (checkForWinner()) {
            showWinner()
        } else if (isBoardFull()) {
            showDraw()
        } else {
            isPlayer1Turn = !isPlayer1Turn
            if (mode == "AI" && !isPlayer1Turn) {
                makeAIMove() // AI's turn after player move
            }
        }
    }

    private fun makeAIMove() {
        // Find empty spots
        val emptySpots = mutableListOf<Pair<Int, Int>>()
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoard[i][j] == 0) {
                    emptySpots.add(Pair(i, j))
                }
            }
        }

        // Choose a random empty spot for AI's move
        if (emptySpots.isNotEmpty()) {
            val (row, col) = emptySpots[Random.nextInt(emptySpots.size)]
            val gridTiles = listOf(
                findViewById<ImageView>(R.id.iv1),
                findViewById<ImageView>(R.id.iv2),
                findViewById<ImageView>(R.id.iv3),
                findViewById<ImageView>(R.id.iv4),
                findViewById<ImageView>(R.id.iv5),
                findViewById<ImageView>(R.id.iv6),
                findViewById<ImageView>(R.id.iv7),
                findViewById<ImageView>(R.id.iv8),
                findViewById<ImageView>(R.id.iv9)
            )

            // Simulate AI move
            val tile = gridTiles[row * 3 + col]
            tile.setImageResource(if (player2Symbol == "X") R.drawable.symbol_x else R.drawable.symbol_o)
            gameBoard[row][col] = 2

            if (checkForWinner()) {
                showWinner()
            } else if (isBoardFull()) {
                showDraw()
            } else {
                isPlayer1Turn = true // Switch back to player's turn
            }
        }
    }

    private fun checkForWinner(): Boolean {
        // Check rows, columns, and diagonals for a winner
        for (i in 0..2) {
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2] && gameBoard[i][0] != 0)
                return true
            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i] && gameBoard[0][i] != 0)
                return true
        }
        // Check diagonals
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != 0)
            return true
        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0] && gameBoard[0][2] != 0)
            return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoard[i][j] == 0) return false
            }
        }
        return true
    }

    private fun showWinner() {
        val winnerTextView = findViewById<TextView>(R.id.tvWinner)
        val player1ScoreTextView = findViewById<TextView>(R.id.tvPlayer1Score)
        val player2ScoreTextView = findViewById<TextView>(R.id.tvPlayer2Score)

        if (isPlayer1Turn) {
            player1Score++
            player1ScoreTextView.text = player1Score.toString()
            winnerTextView.text = "Player 1 Wins!"
        } else {
            player2Score++
            player2ScoreTextView.text = player2Score.toString()
            winnerTextView.text = if (mode == "AI") "AI Wins!" else "Player 2 Wins!"
        }

        winnerTextView.visibility = View.VISIBLE
        resetBoard()
    }

    private fun showDraw() {
        val drawTextView = findViewById<TextView>(R.id.tvWinner)
        drawTextView.text = "It's a Draw!"
        drawTextView.visibility = View.VISIBLE
        resetBoard()
    }

    private fun resetBoard() {
        gameBoard = Array(3) { IntArray(3) }
        val tiles = listOf(
            findViewById<ImageView>(R.id.iv1),
            findViewById<ImageView>(R.id.iv2),
            findViewById<ImageView>(R.id.iv3),
            findViewById<ImageView>(R.id.iv4),
            findViewById<ImageView>(R.id.iv5),
            findViewById<ImageView>(R.id.iv6),
            findViewById<ImageView>(R.id.iv7),
            findViewById<ImageView>(R.id.iv8),
            findViewById<ImageView>(R.id.iv9)
        )
        for (tile in tiles) {
            tile.setImageResource(R.drawable.tictactoe_logo)
        }
    }
}
