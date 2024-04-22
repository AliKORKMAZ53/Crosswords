package com.example.crosswords2.util

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BolumData")
data class BolumData(
    @PrimaryKey
    @ColumnInfo(name = "bolumNumarasi")
    var bolumNo: Int,

    @ColumnInfo(name = "harflerIndexi")
    var harflerIndexi: ArrayList<String>,

    @ColumnInfo(name = "ilerlemeIndexi")
    var ilerlemeIndexi: ArrayList<String>,

    @ColumnInfo(name = "kesisenSayilar")
    var kesisenSayilar: Array<Int>


)
