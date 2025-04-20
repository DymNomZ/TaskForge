package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.data.Task
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.convertMonth

class CreateTaskActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        var CancelButton = findViewById<TextView>(R.id.cancel_tv)
        var CreateButton = findViewById<TextView>(R.id.create_tv)
        var TrivialBtn = findViewById<Button>(R.id.trivial_btn)
        var EasyBtn = findViewById<Button>(R.id.easy_btn)
        var MediumBtn = findViewById<Button>(R.id.medium_btn)
        var HardBtn = findViewById<Button>(R.id.hard_btn)
        var DatePicker = findViewById<DatePicker>(R.id.datepicker)
        var TaskTitleET = findViewById<EditText>(R.id.task_title_et)

        var selectedDifficulty = "Trivial"

        CancelButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Assign difficulty
        TrivialBtn.setOnClickListener {
            selectedDifficulty = "Trivial"
            Toast.makeText(this, "Task Difficulty: $selectedDifficulty", Toast.LENGTH_LONG).show()
        }
        EasyBtn.setOnClickListener {
            selectedDifficulty = "Easy"
            Toast.makeText(this, "Task Difficulty: $selectedDifficulty", Toast.LENGTH_LONG).show()
        }
        MediumBtn.setOnClickListener {
            selectedDifficulty = "Medium"
            Toast.makeText(this, "Task Difficulty: $selectedDifficulty", Toast.LENGTH_LONG).show()
        }
        HardBtn.setOnClickListener {
            selectedDifficulty = "Hard"
            Toast.makeText(this, "Task Difficulty: $selectedDifficulty", Toast.LENGTH_LONG).show()
        }

        CreateButton.setOnClickListener {

            var month = (DatePicker.month + 1).toString()
            var day = DatePicker.dayOfMonth.toString()
            var year = DatePicker.year.toString()

            month = convertMonth(month)

            TasksActivity.tasks.add(
                Task(
                    TaskTitleET.text.toString(),
                    month + " " + day + " " + year,
                    selectedDifficulty
                )
            )

            var userPrefsManager = UserPreferenceManager(this)
            var username = (application as UserData).username

            userPrefsManager.saveTasksToDevice(this, username, TasksActivity.tasks)

            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
}