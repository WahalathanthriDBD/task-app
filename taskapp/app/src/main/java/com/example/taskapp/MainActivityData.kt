package com.example.taskapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityData:ViewModel() {

    private val _data = MutableLiveData<List<Task>>()

    val data:LiveData<List<Task>> = _data

    fun setData(data:List<Task>){
        _data.value = data
    }
}