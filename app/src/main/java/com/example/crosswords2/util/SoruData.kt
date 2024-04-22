package com.example.crosswords2.util

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SoruData")
data class SoruData(

    @ColumnInfo(name = "bolumNumarasi")
    var bolumNo: Int,

    @ColumnInfo(name = "soruNumarasi")
    var soruNo: Int,

    @ColumnInfo(name = "yatayMi")
    var yatayMi: Boolean,

    @ColumnInfo(name = "tamamlanmisMi", defaultValue = "false")
    var tamamlanmisMi: Boolean,

    @ColumnInfo("konumBaslangicNo")
    var baslangicNo: Int,

    @ColumnInfo("konumBitisNo")
    var bitisNo: Int,

    @ColumnInfo("ipucu")
    var ipucu: String



)
