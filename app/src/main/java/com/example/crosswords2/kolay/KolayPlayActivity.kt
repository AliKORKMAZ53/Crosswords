package com.example.crosswords2.kolay

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
    var touchCounter: Int = 0
    lateinit var viewHolder : TextView
    val theMap = mutableMapOf<Int, ArrayList<Int>>()
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


        BOLUMNO = intent.getIntExtra("bolumNo", 0)

        genelViewModel = GenelViewModel(application)

        /*
                genelViewModel.insertBolumData(BolumData(0,"KAREAXXKRXXİERİK","","1,4,13,16",4))
                genelViewModel.insertSoruData(SoruData(1,0,1,true,false,1,4,"1,2,3,4","Eşkenar Dörtgen"))
                genelViewModel.insertSoruData(SoruData(2,0,2,false,false,1,13,"1,5,9,13","Eşkenar Dörtgen"))
                genelViewModel.insertSoruData(SoruData(3,0,3,false,false,4,16,"4,8,12,16","Ekilmiş"))
                genelViewModel.insertSoruData(SoruData(4,0,4,true,false,13,16,"13,14,15,16","Ekşi bir meyve"))


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
        var arrayofIds = rvAdapter.getArrayOfIds()
        //var counter= 0

        //Bulmacanin Onclick'i
        rvAdapter.setOnClickListener(object :
            RvAdapter.OnClickListener {
            override fun onClick(position: Int, model: HarfKutusuModel, view: TextView) {
                viewHolder=view

                var selectedMap = theMap.filterValues {
                    it.contains(position + 1)
                }


                arrayofIds.forEach {
                    findViewById<View>(it).setBackgroundResource(R.drawable.back)
                }

                if (selectedMap.size == 1) {
                    selectedMap.forEach {
                        it.value.forEach {
                            Log.d("tagu", "${it.toString()} konum")
                            Log.d("tagu", "${arrayofIds.get(it - 1)} arrayofIDs")
                            findViewById<View>(arrayofIds.get(it - 1)).setBackgroundResource(R.drawable.colored_back)
                        }
                        findViewById<TextView>(R.id.hintTw).setText(soruArraylist[it.key - 1].ipucu)
                    }
                } else if (selectedMap.size == 2) {
                    if (touchCounter % 2 == 0) {
                        var twoItemMap = selectedMap.entries.toList()
                        twoItemMap.get(0).value.forEach {
                            findViewById<View>(arrayofIds.get(it - 1)).setBackgroundResource(R.drawable.colored_back)
                        }
                        findViewById<TextView>(R.id.hintTw).setText(soruArraylist[twoItemMap.get(0).key - 1].ipucu)
                        touchCounter++
                    } else {
                        var twoItemMap = selectedMap.entries.toList()
                        twoItemMap.get(1).value.forEach {
                            findViewById<View>(arrayofIds.get(it - 1)).setBackgroundResource(R.drawable.colored_back)
                        }
                        findViewById<TextView>(R.id.hintTw).setText(soruArraylist[twoItemMap.get(1).key - 1].ipucu)
                        touchCounter++
                    }

                } else {
                    Toast.makeText(
                        this@KolayPlayActivity,
                        "${selectedMap.size} :mapsize error",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                view.setBackgroundColor(Color.CYAN)
                Log.d("tagu", view.id.toString())
                Log.d("tagu", position.toString())


            }
        })

        genelViewModel.bolum.observe(this) {
            if (it != null) {
                var size = it?.harflerIndexi?.length
                for (x in 0..size!! - 1) {
                    harflist.add(HarfKutusuModel(it?.harflerIndexi?.get(x).toString()))
                }
                rvAdapter.notifyDataSetChanged()
            }

        }

        genelViewModel.sorular.observe(this, {
            soruArraylist = it as ArrayList

            soruArraylist.forEach { soru -> //4 KERE DONER
                var geciciKonumList = convertStringToIntList(soru.tumKonum) //4 ITEM DONER
                var soruNo = soru.soruNo

                theMap.put(soruNo, geciciKonumList)

            }
        })


    }

    //Klavyenin Onclick'i
    fun buttonClicked(view: View) {
        var button = view as Button
        if(viewHolder!=null){
            viewHolder.setText(button.text)
        }

    }

    fun convertStringToIntList(input: String): ArrayList<Int> {
        val intList = ArrayList<Int>()
        val regex = Regex("\\d+")
        val matches = regex.findAll(input)

        for (match in matches) {
            intList.add(match.value.toInt())
        }

        return intList
    }
}


