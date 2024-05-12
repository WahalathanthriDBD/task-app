package com.example.taskapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    var item:String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "priority")
    var priority: Int,
    @ColumnInfo(name = "deadline")
    var deadline:String?

){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
