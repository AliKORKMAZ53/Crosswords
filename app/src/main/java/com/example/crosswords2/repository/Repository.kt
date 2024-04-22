package com.example.crosswords2.repository

import com.example.crosswords2.db.BolumDao
import com.example.crosswords2.db.SoruDao
import com.example.crosswords2.util.BolumData
import com.example.crosswords2.util.SoruData

class Repository(private val bolumDao: BolumDao, private val soruDao: SoruDao) {
    suspend fun insertBolumData(bolumData: BolumData) {
        bolumDao.insert(bolumData)
    }

    suspend fun updateBolumData(bolumData: BolumData) {
        bolumDao.update(bolumData)
    }

    suspend fun deleteBolumData(bolumData: BolumData) {
        bolumDao.delete(bolumData)
    }

    suspend fun getAllBolumData(): List<BolumData> {
        return bolumDao.getAllBolumData()
    }

    suspend fun getBolumDataByBolumNo(bolumNo: Int): BolumData? {
        return bolumDao.getBolumDataByBolumNo(bolumNo)
    }

    suspend fun insertSoruData(soruData: SoruData) {
        soruDao.insert(soruData)
    }

    suspend fun updateSoruData(soruData: SoruData) {
        soruDao.update(soruData)
    }

    suspend fun deleteSoruData(soruData: SoruData) {
        soruDao.delete(soruData)
    }

    suspend fun getAllSoruData(): List<SoruData> {
        return soruDao.getAllSoruData()
    }

    suspend fun getSoruDataByBolumNo(bolumNo: Int): ArrayList<SoruData> {
        return soruDao.getSoruDataByBolumNo(bolumNo)
    }
}