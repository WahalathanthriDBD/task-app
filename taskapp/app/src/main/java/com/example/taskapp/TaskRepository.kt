package com.example.taskapp

class TaskRepository(private val db:TaskDatabase){

    suspend fun insert(task: Task) = db.getTaskDao().insert(task)
    suspend fun delete(task: Task) = db.getTaskDao().delete(task)

    suspend fun update(task: Task) = db.getTaskDao().update(task)

    fun getAllTasks():List<Task> = db.getTaskDao().getAllTasks()

}