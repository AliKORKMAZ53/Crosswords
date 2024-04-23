package com.example.crosswords2.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BolumData")
data class BolumData(
    @PrimaryKey
    @ColumnInfo(name = "bolumNumarasi")
    var bolumNo: Int,

    @ColumnInfo(name = "harflerIndexi")
    var harflerIndexi: String,

    @ColumnInfo(name = "ilerlemeIndexi")
    var ilerlemeIndexi: String,

    @ColumnInfo(name = "kesisenSayilar")
    var kesisenSayilar: String,

    @ColumnInfo(name = "spanCount")
    var spanCount: Int


)
