package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChooseSymbols : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choose_symbols)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mode = intent.getStringExtra("mode")
        val chooseXImageView = findViewById<ImageView>(R.id.imgageviewChooseX)
        val chooseOImageView = findViewById<ImageView>(R.id.imgageviewChooseO)
        chooseXImageView.setOnClickListener {
            startGame("X", mode)
        }
        chooseOImageView.setOnClickListener {
            startGame("O", mode)
        }

    }
    private fun startGame(symbol: String, mode: String?) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("symbol", symbol)
        intent.putExtra("mode", mode)
        startActivity(intent)
    }
}