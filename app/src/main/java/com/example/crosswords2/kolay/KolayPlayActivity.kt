package com.example.crosswords2.kolay

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.example.crosswords2.tables.BolumData
import com.example.crosswords2.tables.HarfKutusuModel
import com.example.crosswords2.tables.SoruData
import com.example.crosswords2.viewmodel.GenelViewModel

class KolayPlayActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RvAdapter
    lateinit var harflist: ArrayList<HarfKutusuModel>
    var binding: ActivityKolayLevelBirBinding? = null
    var BOLUMNO: Int = 0

    private lateinit var genelViewModel: GenelViewModel
    lateinit var soruArraylist: ArrayList<SoruData>

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

        /*
        genelViewModel.insertBolumData(BolumData(0,"KAREAXXKRXXİERİK","","1,4,13,16",4))
        genelViewModel.insertSoruData(SoruData(1,0,1,true,false,1,4,"Eşkenar Dörtgen"))
        genelViewModel.insertSoruData(SoruData(2,0,2,false,false,1,13,"Eşkenar Dörtgen"))
        genelViewModel.insertSoruData(SoruData(3,0,3,false,false,4,16,"Ekilmiş"))
        genelViewModel.insertSoruData(SoruData(4,0,4,true,false,13,16,"Ekşi bir meyve"))

         */

        harflist = ArrayList()

        genelViewModel.getBolumData(BOLUMNO)
        genelViewModel.getSorularData(BOLUMNO)


        recyclerView = findViewById(R.id.recyclerView)



        //SPAN COUNT DINAMIC OLABILIR MI?
        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 4)
        //recyclerView.layoutManager = layoutManager
        rvAdapter = RvAdapter(harflist, this)
        binding?.recyclerView?.adapter = rvAdapter

        //Bulmacanin Onclick'i
        rvAdapter.setOnClickListener(object :
            RvAdapter.OnClickListener {
            override fun onClick(position: Int, model: HarfKutusuModel,view: View) {
                view.setBackgroundColor(Color.CYAN)
                
            }
        })

        genelViewModel.bolum.observe(this) {
            var size = it?.harflerIndexi?.length
            for (x in 0 .. size!!-1){
                harflist.add(HarfKutusuModel(it?.harflerIndexi?.get(x).toString()))
                }
            rvAdapter.notifyDataSetChanged()
        }
        genelViewModel.sorular.observe(this,{
            soruArraylist = it as ArrayList

        })


    }

    //Klavyenin Onclick'i
    fun buttonClicked(view: View) {
        var button = view as Button
        Toast.makeText(this@KolayPlayActivity, button.text, Toast.LENGTH_SHORT).show()

    }
}