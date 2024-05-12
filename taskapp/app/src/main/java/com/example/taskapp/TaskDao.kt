package com.example.taskapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("Select * FROM Task")
    fun getAllTasks():List<Task>

    @Query("SELECT * FROM Task WHERE id=:id")
    fun getOne(id:Int):Task
}