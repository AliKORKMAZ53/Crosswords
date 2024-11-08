package com.example.crosswords2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.crosswords2.tables.BolumData

@Dao
interface BolumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bolumData: BolumData)

    @Update
    suspend fun update(bolumData: BolumData)

    @Query("DELETE FROM BolumData")
    suspend fun deleteAllBolums()

    @Query("SELECT * FROM BolumData WHERE bolumNumarasi = :bolumNo")
    suspend fun getBolumDataByBolumNo(bolumNo: Int): BolumData?
}