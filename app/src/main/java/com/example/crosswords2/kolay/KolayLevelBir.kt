package com.example.crosswords2.kolay

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crosswords2.R
import com.example.crosswords2.RvAdapter
import com.example.crosswords2.databinding.ActivityKolayLevelBirBinding
import com.example.crosswords2.tables.HarfKutusuModel
import com.example.crosswords2.viewmodel.GenelViewModel

class KolayLevelBir : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RvAdapter
    lateinit var harflist: ArrayList<HarfKutusuModel>
    var binding: ActivityKolayLevelBirBinding? = null
    var BOLUMNO: Int = 0

    private lateinit var genelViewModel: GenelViewModel


    //Viewbinding kullanıldı, bundan sonra viewlar binding ile çağrılacak
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Binding
        binding = ActivityKolayLevelBirBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        BOLUMNO=intent.getIntExtra("bolumNo",0)

        genelViewModel= GenelViewModel(application)
        genelViewModel.getBolumData(BOLUMNO)
        genelViewModel.getSorularData(BOLUMNO)

        genelViewModel.bolum.observe(this,{

        })
        genelViewModel.sorular.observe(this,{

        })

        recyclerView = findViewById(R.id.recyclerView)

        harflist = ArrayList()

        //SPAN COUNT DINAMIC OLABILIR MI?
        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 4)
        //recyclerView.layoutManager = layoutManager
        rvAdapter = RvAdapter(harflist, this)
        binding?.recyclerView?.adapter = rvAdapter

        rvAdapter.setOnClickListener(object :
            RvAdapter.OnClickListener {
            override fun onClick(position: Int, model: HarfKutusuModel) {
                Toast.makeText(this@KolayLevelBir, model.harf, Toast.LENGTH_SHORT).show()
            }
        })




        harflist.add(HarfKutusuModel("K"))
        harflist.add(HarfKutusuModel("A"))
        harflist.add(HarfKutusuModel("R"))
        harflist.add(HarfKutusuModel("E"))
        harflist.add(HarfKutusuModel("A"))
        harflist.add(HarfKutusuModel("X"))
        harflist.add(HarfKutusuModel("X"))
        harflist.add(HarfKutusuModel("K"))
        harflist.add(HarfKutusuModel("R"))
        harflist.add(HarfKutusuModel("X"))
        harflist.add(HarfKutusuModel("X"))
        harflist.add(HarfKutusuModel("I"))
        harflist.add(HarfKutusuModel("E"))
        harflist.add(HarfKutusuModel("R"))
        harflist.add(HarfKutusuModel("I"))
        harflist.add(HarfKutusuModel("K"))






        rvAdapter.notifyDataSetChanged()
    }

    fun buttonClicked(view: View) {
        var button = view as Button
        Toast.makeText(this@KolayLevelBir, button.text, Toast.LENGTH_SHORT).show()

    }
}