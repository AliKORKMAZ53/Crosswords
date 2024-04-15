package com.example.crosswords2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crosswords2.kolay.KolayLevelSelection
import com.example.crosswords2.orta.OrtaLevelSelection
import com.example.crosswords2.zor.ZorLevelSelection

class ModeSelection : AppCompatActivity(), View.OnClickListener{
    private lateinit var buttonKolay: Button
    private lateinit var buttonOrta: Button
    private lateinit var buttonZor: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buttonKolay = findViewById(R.id.button2)
        buttonOrta = findViewById(R.id.button3)
        buttonZor = findViewById(R.id.button4)

        buttonKolay.setOnClickListener(this)
        buttonOrta.setOnClickListener(this)
        buttonZor.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button2 -> {
                val intent = Intent(this@ModeSelection, KolayLevelSelection::class.java)
                startActivity(intent)
            }
            R.id.button3 -> {
                val intent = Intent(this@ModeSelection, OrtaLevelSelection::class.java)
                startActivity(intent)
            }
            R.id.button4 -> {
                val intent = Intent(this@ModeSelection, ZorLevelSelection::class.java)
                startActivity(intent)
            }
            }
    }
}