package com.example.crosswords2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosswords2.repository.Repository
import com.example.crosswords2.util.SoruData
import kotlinx.coroutines.launch

class SoruDataViewModel(private val repository: Repository) : ViewModel() {
    // Define LiveData or StateFlow variables to hold the data

    fun insertSoruData(soruData: SoruData) {
        viewModelScope.launch {
            repository.insertSoruData(soruData)
        }
    }

    // Implement other CRUD operations and data retrieval methods
}