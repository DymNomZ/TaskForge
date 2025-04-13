package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.dymnomz.task_forge.data.Task
import com.dymnomz.task_forge.helper.convertMonth

class EditTaskActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        var CancelButton = findViewById<TextView>(R.id.cancel_tv)
        var SaveButton = findViewById<TextView>(R.id.save_tv)
        var TrivialBtn = findViewById<Button>(R.id.trivial_btn)
        var EasyBtn = findViewById<Button>(R.id.easy_btn)
        var MediumBtn = findViewById<Button>(R.id.medium_btn)
        var HardBtn = findViewById<Button>(R.id.hard_btn)
        var DatePicker = findViewById<DatePicker>(R.id.datepicker)
        var TaskTitleET = findViewById<EditText>(R.id.task_title_et)

        var selectedDifficulty = "Trivial"

        intent?.let {
            it.getStringExtra("difficulty")?.let { difficulty ->
                selectedDifficulty = difficulty
            }
            it.getStringExtra("title")?.let { title ->
                TaskTitleET.setText(title)
            }
        }

        CancelButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Assign difficulty
        TrivialBtn.setOnClickListener { selectedDifficulty = "Trivial" }
        EasyBtn.setOnClickListener { selectedDifficulty = "Easy" }
        MediumBtn.setOnClickListener { selectedDifficulty = "Medium" }
        HardBtn.setOnClickListener { selectedDifficulty = "Hard" }

        SaveButton.setOnClickListener {

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

            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}