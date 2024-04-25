package com.example.crosswords2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.example.crosswords2.tables.BolumData
import com.example.crosswords2.tables.SoruData

@Database(entities = [BolumData::class, SoruData::class], exportSchema = false, version = 2)
abstract class GenelDatabase: RoomDatabase() {
    abstract fun bolumDao(): BolumDao
    abstract fun soruDao(): SoruDao

    companion object {
        private var instance: GenelDatabase? = null

        fun getGenelDatabase(context: Context): GenelDatabase? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    GenelDatabase::class.java,
                    "genel.db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}