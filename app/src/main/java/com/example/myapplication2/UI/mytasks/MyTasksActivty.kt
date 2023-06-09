package com.example.myapplication2.UI.mytasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyTasksActivty : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var newTaskButton:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tasks_activty)
        newTaskButton=findViewById(R.id.newTaskButton)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }
}