package com.example.taskapp

import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: taskadapter
    private lateinit var viewModel: MainActivityData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewTasks)
        val repository = TaskRepository(TaskDatabase.getInstance(this))

        viewModel = ViewModelProvider(this)[MainActivityData::class.java]

        viewModel.data.observe(this) {
            adapter = taskadapter(it, repository, viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllTasks()

            runOnUiThread {
                viewModel.setData(data)
            }


            val addTask: FloatingActionButton = findViewById(R.id.AddTask)


            addTask.setOnClickListener {
                displayAlert(repository)
            }



        }



    }

    private fun displayAlert(repository: TaskRepository) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Enter New Task:")
        builder.setMessage("Enter Task:")

        // Set up the layout for the dialog
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        // Add EditText fields for three inputs
        val taskInput = EditText(this)
        taskInput.hint = "Enter Task"
        layout.addView(taskInput)

        val deadlineInput = EditText(this)
        deadlineInput.hint = "Enter Deadline"
        layout.addView(deadlineInput)

        val descriptionInput = EditText(this)
        descriptionInput.hint = "Enter Description"
        layout.addView(descriptionInput)

        val priorityInput = EditText(this)
        priorityInput.hint = "Enter Priority"
        priorityInput.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(priorityInput)

        builder.setView(layout)

        builder.setPositiveButton("OK") { dialog, which ->
            val task = taskInput.text.toString()
            val deadline = deadlineInput.text.toString()
            val description = descriptionInput.text.toString()
            val priority = priorityInput.text.toString().toIntOrNull() ?: 0

            CoroutineScope(Dispatchers.IO).launch {

                repository.insert(Task(task, description, priority, deadline))
                val data = repository.getAllTasks()
                runOnUiThread{
                    viewModel.setData(data)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun displayUpdateDialog(repository: TaskRepository,task:Task) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Task:")
        builder.setMessage("Update Task Details:")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val taskInput = EditText(this)
        taskInput.hint = "Enter Task"
        taskInput.setText(task.item) // Pre-fill the task title
        layout.addView(taskInput)

        val deadlineInput = EditText(this)
        deadlineInput.hint = "Enter Deadline"
        deadlineInput.setText(task.deadline) // Pre-fill the deadline
        layout.addView(deadlineInput)

        val descriptionInput = EditText(this)
        descriptionInput.hint = "Enter Description"
        descriptionInput.setText(task.description) // Pre-fill the description
        layout.addView(descriptionInput)

        val priorityInput = EditText(this)
        priorityInput.hint = "Enter Priority"
        priorityInput.inputType = InputType.TYPE_CLASS_NUMBER
        priorityInput.setText(task.priority.toString()) // Pre-fill the priority
        layout.addView(priorityInput)

        builder.setView(layout)

        builder.setPositiveButton("Update") { dialog, which ->
            val updatedTask = Task(
                taskInput.text.toString(),
                descriptionInput.text.toString(),
                priorityInput.text.toString().toIntOrNull() ?: 0,
                deadlineInput.text.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                repository.update(updatedTask)
                val data = repository.getAllTasks()
                runOnUiThread {
                    viewModel.setData(data)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}



