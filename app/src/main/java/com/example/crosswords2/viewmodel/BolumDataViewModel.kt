package com.example.crosswords2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosswords2.repository.Repository
import com.example.crosswords2.util.BolumData
import kotlinx.coroutines.launch

class BolumDataViewModel(private val repository: Repository) : ViewModel() {
    // Define LiveData or StateFlow variables to hold the data

    fun insertBolumData(bolumData: BolumData) {
        viewModelScope.launch {
            repository.insertBolumData(bolumData)
        }
    }

    fun  getBolumData(bolumNo: Int){
        viewModelScope.launch {
            repository.getBolumDataByBolumNo(bolumNo)
        }
    }


    // Implement other CRUD operations and data retrieval methods
}