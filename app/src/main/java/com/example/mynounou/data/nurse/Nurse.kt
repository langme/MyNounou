package com.example.mynounou.data.nurse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "nurse" , indices = [Index(value = ["emailUser", "nurseId", "siret"], unique = true)])
data class Nurse (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nurseId")
    var nurseId: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String = "",
    @ColumnInfo(name = "lastName")
    var lastName: String = "",
    @ColumnInfo(name = "emailUser")
    var emailUser: String ="",
    @ColumnInfo(name = "isvisible")
    var isVisible: Boolean = true,
    @ColumnInfo(name = "siret")
    var siret: Int = 0,
    @ColumnInfo(name = "address")
    var address: String ="",
    @ColumnInfo(name = "priceHour")
    var priceHour: Float = 0f,
    @ColumnInfo(name = "priceBrunch")
    var priceBrunch: Float = 0f,
    @ColumnInfo(name = "priceDej")
    var priceDej: Float = 0f,
    @ColumnInfo(name = "priceGouter")
    var priceGouter: Float = 0f,
    @ColumnInfo(name = "priceService")
    var priceService: Float = 0f,
    @ColumnInfo(name = "hourPerMonth")
    var hourPerMonth: Float = 0f
){
}
