package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class CreateTaskActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        var CancelButton = findViewById<TextView>(R.id.cancel_tv)
        var CreateButton = findViewById<TextView>(R.id.create_tv)

        CancelButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
        }

        CreateButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)

            //create task logic here
        }

    }
}