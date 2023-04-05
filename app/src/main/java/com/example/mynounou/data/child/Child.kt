package com.example.mynounou.data.child

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "child" , indices = [Index(value = ["emailUser", "userId", "nurseId"], unique = true)])
data class Child (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String = "",
    @ColumnInfo(name = "lastName")
    var lastName: String = "",
    @ColumnInfo(name = "emailUser")
    var emailUser: String ="",
    @ColumnInfo(name = "isvisible")
    var isVisible: Boolean = true,
    @ColumnInfo(name = "nurseId")
    var nurseId: Int = 0,
    @ColumnInfo(name = "age")
    var age: Int = 0,
    @ColumnInfo(name = "hourPerMonth")
    var hourPerMonth: Float = 0f
){
}
