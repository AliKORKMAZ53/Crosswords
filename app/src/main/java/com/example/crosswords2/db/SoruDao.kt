package com.example.crosswords2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.crosswords2.tables.SoruData

@Dao
interface SoruDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(soruData: SoruData)

    //UPDATE VE DELETE FONKSİYONLARI HANGİ RECORD ÜZERİNDE ÇALIŞACAĞINI BİLMİYOR
    @Update
    suspend fun update(soruData: SoruData)

    @Delete
    suspend fun delete(soruData: SoruData)


    @Query("SELECT * FROM SoruData WHERE bolumNumarasi = :bolumNo")
    suspend fun getSoruDataByBolumNo(bolumNo: Int): List<SoruData>
}