package com.example.crosswords2.kolay

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crosswords2.R
import com.example.crosswords2.RvAdapter
import com.example.crosswords2.databinding.ActivityKolayLevelBirBinding
import com.example.crosswords2.tables.HarfKutusuModel
import com.example.crosswords2.tables.SoruData
import com.example.crosswords2.viewmodel.GenelViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class KolayPlayActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RvAdapter
    lateinit var harflist: ArrayList<HarfKutusuModel>
    var binding: ActivityKolayLevelBirBinding? = null
    var BOLUMNO: Int = 0
    var touchCounter: Int = 0
    lateinit var textViewHolder: TextView
    lateinit var viewHolder: View
    lateinit var arrayofTextViewIds: ArrayList<Int>
    lateinit var arrayofViewIds: ArrayList<Int>
    lateinit var nextBox: View
    var selectedBoxesIndexes = ArrayList<Int>()
    val theMap = mutableMapOf<Int, ArrayList<Int>>()
    lateinit var selectedMap: Map<Int, ArrayList<Int>>
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


        settleRecyclerView()


        //Bulmacanin Onclick'i
        rvAdapter.setOnClickListener(object :
            RvAdapter.OnClickListener {
            override fun onClick(
                position: Int,
                model: HarfKutusuModel,
                textView: TextView,
                viewItem: View
            ) {
                textViewHolder = textView
                viewHolder = viewItem
                Log.d("tags", "${touchCounter.toString()} item bas click")
                selectedMap = theMap.filterValues {
                    it.contains(position + 1)
                }


                arrayofTextViewIds.forEach {
                    findViewById<View>(it).setBackgroundResource(R.drawable.back)
                }

                if (selectedMap.size == 1) { //kesisen kutu degil
                    selectedMap.forEach {
                        it.value.forEach {
                            findViewById<View>(arrayofTextViewIds.get(it - 1)).setBackgroundResource(
                                R.drawable.colored_back
                            )
                        }
                        findViewById<TextView>(R.id.hintTw).setText(soruArraylist[it.key - 1].ipucu)
                        if (soruArraylist[it.key - 1].yatayMi) {
                            touchCounter = 0 // yatay
                        } else {
                            touchCounter = 1 //dikey
                        }
                    }
                } else if (selectedMap.size == 2) { //kesisen kutu ise
                    if (touchCounter % 2 == 0) { //yatay duzlem
                        //twoItemMap - Kesisen kareler icin Map
                        var twoItemMap = selectedMap.entries.toList()
                        twoItemMap.get(0).value.forEach {
                            findViewById<View>(arrayofTextViewIds.get(it - 1)).setBackgroundResource(
                                R.drawable.colored_back
                            )
                        }
                        //var yataymi= soruArraylist.get(twoItemMap.get(0).value[0])

                        findViewById<TextView>(R.id.hintTw).setText(soruArraylist[twoItemMap.get(0).key - 1].ipucu)
                        touchCounter = 1
                    } else { //dikey duzlem
                        var twoItemMap = selectedMap.entries.toList()
                        twoItemMap.get(1).value.forEach {
                            findViewById<View>(arrayofTextViewIds.get(it - 1)).setBackgroundResource(
                                R.drawable.colored_back
                            )
                            selectedBoxesIndexes.add(arrayofTextViewIds.get(it - 1))
                        }
                        findViewById<TextView>(R.id.hintTw).setText(soruArraylist[twoItemMap.get(1).key - 1].ipucu)
                        touchCounter = 0
                    }
                    Log.d("tags", "${touchCounter.toString()} item son click")
                } else {
                    Toast.makeText(
                        this@KolayPlayActivity,
                        "${selectedMap.size} :mapsize error",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                textView.setBackgroundColor(Color.CYAN)
                Log.d("tagu", textView.id.toString())
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

    GlobalScope.launch {
        delay(500)
        if(recyclerView.getChildAt(0)!=null){
            recyclerView.getChildAt(0).callOnClick()
        }else{
            Log.d("tags","RW empty")
        }
    }

    }
    fun settleRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView)
        //SPAN COUNT DINAMIC OLABILIR MI?
        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 4)
        rvAdapter = RvAdapter(harflist, this)
        binding?.recyclerView?.adapter = rvAdapter
        arrayofTextViewIds = rvAdapter.getArrayOfTextViewIds()
        arrayofViewIds = rvAdapter.getArrayOfViewIds()
    }


    //Klavyenin Onclick'i
    fun buttonClicked(view: View) {
        var button = view as Button
        if (textViewHolder != null) {
            textViewHolder.setText(button.text)

            var a = recyclerView.indexOfChild(viewHolder)
            var b = recyclerView.childCount
            Log.d("tags", "${touchCounter.toString()} klavye bas click")


            if (a + 1 == b) {
            } else { //last item değilse
                if (touchCounter % 2 == 1) { // dikeyse

                    recyclerView.getChildAt(a + 4).callOnClick()

                } else {//yataysa
                    recyclerView.getChildAt(a + 1).callOnClick()
                }

            }


            Log.d("tags", "${touchCounter.toString()} klavye son click")
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


