package com.example.crosswords2.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosswords2.db.GenelDatabase
import com.example.crosswords2.repository.Repository
import com.example.crosswords2.tables.BolumData
import com.example.crosswords2.tables.SoruData
import kotlinx.coroutines.launch

class GenelViewModel(application: Application) : ViewModel() {
    // Define LiveData or StateFlow variables to hold the data

    val repository : Repository
    val bolum= MutableLiveData<BolumData?>()
    val sorular= MutableLiveData<ArrayList<SoruData>>()

    init {
        val db= GenelDatabase.getGenelDatabase(application)
        val bolumDao= db?.bolumDao()
        val soruDao= db?.soruDao()
        repository = Repository(bolumDao!!,soruDao!!)

    }
    fun insertBolumData(bolumData: BolumData) {
        viewModelScope.launch {
            repository.insertBolumData(bolumData)
        }
    }

    fun  getBolumData(bolumNo: Int){
        viewModelScope.launch {
            var data=repository.getBolumDataByBolumNo(bolumNo)
            bolum.postValue(data)
        }
    }

    fun insertSoruData(soruData: SoruData) {
        viewModelScope.launch {
            repository.insertSoruData(soruData)
        }
    }

    fun getSorularData(bolumNo: Int){
        viewModelScope.launch{
            var data = repository.getSoruDataByBolumNo(bolumNo)
            sorular.postValue(data)
        }
    }



}