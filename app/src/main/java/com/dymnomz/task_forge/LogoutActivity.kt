package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class LogoutActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        val CancelButton = findViewById<Button>(R.id.cancel_button)
        CancelButton.setOnClickListener {
            Log.e("CancelButton", "CancelButton is clicked")

            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
        }

        val LogoutButton = findViewById<Button>(R.id.logout_btn)
        LogoutButton.setOnClickListener {
            Log.e("LogoutButton", "LogoutButton is clicked")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}