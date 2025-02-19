package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class LandingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val ToProfileButton = findViewById<Button>(R.id.to_profile_btn)
        ToProfileButton.setOnClickListener {
            Log.e("ToProfileButton", "ToProfileButton is clicked")

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val ToSettingsButton = findViewById<Button>(R.id.to_settings_btn)
        ToSettingsButton.setOnClickListener {
            Log.e("ToSettingsButton", "ToSettingsButton is clicked")

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val LogoutButton = findViewById<ImageButton>(R.id.logout_btn)
        LogoutButton.setOnClickListener {
            Log.e("LogoutButton", "LogoutButton is clicked")

            val intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
        }
    }
}