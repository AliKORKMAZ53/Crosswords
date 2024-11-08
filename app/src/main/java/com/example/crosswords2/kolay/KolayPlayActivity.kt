package com.example.crosswords2.kolay

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.example.crosswords2.R
import com.example.crosswords2.databinding.ActivityKolayLevelBirBinding
import com.example.crosswords2.tables.BolumData
import com.example.crosswords2.tables.HarfKutusuModel
import com.example.crosswords2.tables.SoruData
import com.example.crosswords2.viewmodel.GenelViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class KolayPlayActivity : AppCompatActivity() {
    lateinit var harflist: ArrayList<HarfKutusuModel>
    var binding: ActivityKolayLevelBirBinding? = null
    var BOLUMNO: Int = 0
    var nextIndex: Int = 0
    var seciliindex: Int = 0
    var counter : Int = 0
    var toggle = 0
    var textViewHolder: TextView? = null
    lateinit var arrayofTextViewIds: ArrayList<Int>
    lateinit var arrayofViewIds: ArrayList<Int>
    lateinit var kesisenSayilar: ArrayList<Int>
    var seciliKonumunIndexleri = ArrayList<Int>()
    val theMap = mutableMapOf<Int, ArrayList<Int>>()
    lateinit var selectedMap: Map<Int, ArrayList<Int>>
    private lateinit var genelViewModel: GenelViewModel
    lateinit var soruArraylist: ArrayList<SoruData>
    var selectedSquare: TextView? = null
    lateinit var grid: GridLayout

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
        grid = findViewById(R.id.oldRecyclerViewNowGrid)

        BOLUMNO = intent.getIntExtra("bolumNo", 1)

        genelViewModel = GenelViewModel(application)


        //  genelViewModel.insertBolumData(BolumData(1,"KARE--------A--T--------R--I--------ERIK--------------------------------------------------------------------------------------------------------","","0,3,36,39",12))
        //  genelViewModel.insertSoruData(SoruData(1,1,1,true,false,0,3,"0,1,2,3","Eşkenar Dörtgen"))
        //  genelViewModel.insertSoruData(SoruData(2,1,2,false,false,3,39,"3,15,27,39","Ahlak"))
        //  genelViewModel.insertSoruData(SoruData(3,1,3,false,false,0,36,"0,12,24,36","Dörtgen"))
        //  genelViewModel.insertSoruData(SoruData(4,1,4,true,false,36,39,"36,37,38,39","Ekşi bir meyve"))
        // genelViewModel.deleteBolums()


        harflist = ArrayList()

        genelViewModel.getBolumData(BOLUMNO)
        genelViewModel.getSorularData(BOLUMNO)


        //      textView.setBackgroundColor(Color.CYAN)
        //    Log.d("tagu", textView.id.toString())
        //  Log.d("tagu", position.toString())


        genelViewModel.bolum.observe(this) {
            if (it != null) {
                var size = it?.harflerIndexi?.length
                for (x in 0..size!! - 1) {
                    //harflist.add(HarfKutusuModel(it?.harflerIndexi?.get(x).toString()))
                    var v = grid.getChildAt(x) as TextView
                    v.text = it?.harflerIndexi?.get(x).toString()
                    if (v.text == "-") {
                        v.visibility = View.INVISIBLE
                    } else {
                        v.text = ""
                    }

                }
                kesisenSayilar = convertStringToIntList(it.kesisenSayilar)

            }
        }

        //tum sorulari arraya aktar
        genelViewModel.sorular.observe(this, {
            soruArraylist = it as ArrayList
            soruArraylist.forEach { soru -> //4 KERE DONER
                var geciciKonumList = convertStringToIntList(soru.tumKonum) //4 ITEM DONER
                var soruNo = soru.soruNo
                theMap.put(soruNo, geciciKonumList)
            }
        })



        GlobalScope.launch {
            delay(1000)
            if (grid.getChildAt(0) != null) {
                grid.getChildAt(0).callOnClick()
            } else {
                Log.d("tags", "Grid empty")
            }
        }

    }


    fun puzzleClicked(view: View) {
        grid.children.forEach {
            it.setBackgroundResource(R.drawable.back)
        }


        //iki kere tiklandi mi
        var booleanTwiceClicked = false
        if (selectedSquare!=null){//en ilk tiklama kontrol
            booleanTwiceClicked = isItClickedTwice(view as TextView)
        }

        selectedSquare = view as TextView
        var parent = selectedSquare!!.parent as ViewGroup
        var seciliindex = parent.indexOfChild(selectedSquare)

        //All Logic

        //Secili index hangi soruya ya da sorulara(kesisen sayiysa) denk dusuyor
        selectedMap = theMap.filterValues {
            it.contains(seciliindex)
            //Ornek: {1=[0, 1, 2, 3], 3=[0, 12, 24, 36]}
        }
        var list = selectedMap.toList()


        //Secilmis kutu kesisiyorsa
        if(kesisenSayilar.contains(seciliindex)){
            if(booleanTwiceClicked){//ve iki kere tiklanmissa
                toggleValue()
                seciliKonumunIndexleri = list[toggle].second
                //secilikonumindexler ornek: [0, 1, 2, 3]
                //komple soruyu boya
                seciliKonumunIndexleri.forEach {
                    grid.getChildAt(it).setBackgroundResource(R.drawable.colored_back)
                }
                Log.d("onemli", seciliKonumunIndexleri.toString())
                try {
                    if (seciliKonumunIndexleri.indexOf(seciliindex) + 1 == seciliKonumunIndexleri.size) {

                    } else {
                        nextIndex = seciliKonumunIndexleri.indexOf(seciliindex) + 1
                    }

                } catch (e: Exception) {
                    Log.d("onemli", e.toString())
                }
            }else{
                seciliKonumunIndexleri = list[0].second
                //secilikonumindexler ornek: [0, 1, 2, 3]
                //komple soruyu boya
                seciliKonumunIndexleri.forEach {
                    grid.getChildAt(it).setBackgroundResource(R.drawable.colored_back)
                }
                Log.d("onemli", seciliKonumunIndexleri.toString())
                try {
                    if (seciliKonumunIndexleri.indexOf(seciliindex) + 1 == seciliKonumunIndexleri.size) {

                    } else {
                        nextIndex = seciliKonumunIndexleri.indexOf(seciliindex) + 1
                    }

                } catch (e: Exception) {
                    Log.d("onemli", e.toString())
                }
            }


        }else{//Secilmis kutu kesismiyorsa

            seciliKonumunIndexleri = list[0].second
            //secilikonumindexler ornek: [0, 1, 2, 3]
            //komple soruyu boya
            seciliKonumunIndexleri.forEach {
                grid.getChildAt(it).setBackgroundResource(R.drawable.colored_back)
            }
            Log.d("onemli", seciliKonumunIndexleri.toString())
            try {
                if (seciliKonumunIndexleri.indexOf(seciliindex) + 1 == seciliKonumunIndexleri.size) {

                } else {
                    nextIndex = seciliKonumunIndexleri.indexOf(seciliindex) + 1
                }

            } catch (e: Exception) {
                Log.d("onemli", e.toString())
            }
        }
        view.setBackgroundResource(R.drawable.dark_colored_back)
    }

    //Klavyenin Onclick'i
    fun keyboardButtonClicked(view: View) {
        selectedSquare!!.text= (view as Button).text
        if(nextIndex!=null){
            grid.getChildAt(seciliKonumunIndexleri.get(nextIndex)).callOnClick()
        }

    }

    fun toggleValue() {
        toggle = 1 - toggle
    }
    private fun isItClickedTwice(view: TextView): Boolean {
        return selectedSquare!!.id==view.id
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


