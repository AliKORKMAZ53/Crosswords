package com.example.crosswords2.kolay

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crosswords2.GridAdapter
import com.example.crosswords2.R
import com.example.crosswords2.util.harfKutusuModel

class KolayLevelBir : AppCompatActivity() {
    private lateinit var bulmacaGridView: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kolay_level_bir)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bulmacaGridView = findViewById(R.id.puzzleGrid)
        val bulmacaArrayList: ArrayList<harfKutusuModel> = ArrayList<harfKutusuModel>()
        bulmacaArrayList.add(harfKutusuModel("K"))
        bulmacaArrayList.add(harfKutusuModel("A"))
        bulmacaArrayList.add(harfKutusuModel("R"))
        bulmacaArrayList.add(harfKutusuModel("E"))

        val adapter = GridAdapter(this, bulmacaArrayList)
        bulmacaGridView.adapter = adapter
    }
}