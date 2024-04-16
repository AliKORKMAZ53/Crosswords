package com.example.crosswords2.kolay

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crosswords2.R

class KolayLevelSelection : AppCompatActivity() {
    private lateinit var buttonLevelBir: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kolay_level_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buttonLevelBir = findViewById(R.id.buttonL1)
        buttonLevelBir.setOnClickListener {
            val intent = Intent(this@KolayLevelSelection, KolayLevelBir::class.java)
            startActivity(intent)
        }
    }
}