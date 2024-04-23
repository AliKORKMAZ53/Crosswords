package com.example.crosswords2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.crosswords2.tables.BolumData

@Dao
interface BolumDao {
    @Insert
    suspend fun insert(bolumData: BolumData)

    @Update
    suspend fun update(bolumData: BolumData)

    @Delete
    suspend fun delete(bolumData: BolumData)

    @Query("SELECT * FROM BolumData WHERE bolumNumarasi = :bolumNo")
    suspend fun getBolumDataByBolumNo(bolumNo: Int): BolumData?
}