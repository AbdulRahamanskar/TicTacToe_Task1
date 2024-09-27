package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val playAIButton = findViewById<ImageView>(R.id.btnPlayAI)
        val playFriendButton = findViewById<ImageView>(R.id.btnPlayFriend)
        playAIButton.setOnClickListener {
            val intent= Intent(this,ChooseSymbols::class.java)
            intent.putExtra("mode","AI")
            startActivity(intent)
        }
        playFriendButton.setOnClickListener {
            val intent= Intent(this,ChooseSymbols::class.java)
            intent.putExtra("mode","Friend")
            startActivity(intent)
        }
    }
}