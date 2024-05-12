package com.example.taskapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class taskadapter(items:List<Task>,repository: TaskRepository,viewModel: MainActivityData):Adapter<taskViewHolder>() {

    var context: Context? = null
    val items = items
    val repository = repository
    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)

        context = parent.context



        return taskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: taskViewHolder, position: Int) {
        holder.textViewTitle.text = items.get(position).item
        holder.textViewDeadline.text = items.get(position).deadline
        holder.deletebtn.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                repository.delete(items.get(position))
                val data = repository.getAllTasks()
                withContext(Dispatchers.Main){
                    viewModel.setData(data)

                }
            }
            Toast.makeText(context,"Task Removed",Toast.LENGTH_SHORT).show()
        }



    }
}