package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var AboutTheDevButton = findViewById<Button>(R.id.developer_btn)
        var ToggleDarkModeButton = findViewById<Button>(R.id.dark_mode_btn)
        var ResetProgressButton = findViewById<Button>(R.id.reset_btn)
        var DeleteAccButton = findViewById<Button>(R.id.delete_acc_btn)
        var LogoutButton = findViewById<Button>(R.id.logout_btn)

        AboutTheDevButton.setOnClickListener {
            val intent = Intent(this, DeveloperActivity::class.java)
            startActivity(intent)
        }

        ToggleDarkModeButton.setOnClickListener {
            //logic here
        }

        ResetProgressButton.setOnClickListener {
            //logic here
        }

        DeleteAccButton.setOnClickListener {
            //logic here
        }

        LogoutButton.setOnClickListener {
            //popup
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}