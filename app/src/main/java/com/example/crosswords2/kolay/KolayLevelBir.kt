package com.example.crosswords2.kolay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crosswords2.R
import com.example.crosswords2.RvAdapter
import com.example.crosswords2.util.HarfKutusuModel

class KolayLevelBir : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RvAdapter
    lateinit var harflist: ArrayList<HarfKutusuModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kolay_level_bir)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)

        harflist = ArrayList()

        val layoutManager = GridLayoutManager(this,12)
        recyclerView.layoutManager = layoutManager
        rvAdapter = RvAdapter(harflist,this)
        recyclerView.adapter = rvAdapter

        for (i in 1..120){
            harflist.add(HarfKutusuModel("A"))

        }

        rvAdapter.notifyDataSetChanged()
    }
}