package com.example.crosswords2.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.crosswords2.util.SoruData

interface SoruDao {
    @Insert
    suspend fun insert(soruData: SoruData)

    //UPDATE VE DELETE FONKSİYONLARI HANGİ RECORD ÜZERİNDE ÇALIŞACAĞINI BİLMİYOR
    @Update
    suspend fun update(soruData: SoruData)

    @Delete
    suspend fun delete(soruData: SoruData)

    @Query("SELECT * FROM SoruData")
    suspend fun getAllSoruData(): ArrayList<SoruData>

    @Query("SELECT * FROM SoruData WHERE bolumNumarasi = :bolumNo")
    suspend fun getSoruDataByBolumNo(bolumNo: Int): ArrayList<SoruData>
}