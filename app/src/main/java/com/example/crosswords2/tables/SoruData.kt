package com.example.crosswords2.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SoruData")
data class SoruData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "soru_id")
    var soru_id: Int,

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

    @ColumnInfo("tumKonum")
    var tumKonum: String,

    @ColumnInfo("ipucu")
    var ipucu: String



)
