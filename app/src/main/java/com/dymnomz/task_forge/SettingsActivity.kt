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

        val ToProfileButton = findViewById<Button>(R.id.to_profile_btn)
        ToProfileButton.setOnClickListener {
            Log.e("ToProfileButton", "ToProfileButton is clicked")

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val ToLandingButton = findViewById<Button>(R.id.to_landing_btn)
        ToLandingButton.setOnClickListener {
            Log.e("ToLandingButton", "ToLandingButton is clicked")

            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }

        val AboutDevButton = findViewById<Button>(R.id.about_dev_btn)
        AboutDevButton.setOnClickListener {
            Log.e("AboutDevButton", "AboutDevButton is clicked")

            val intent = Intent(this, DeveloperActivity::class.java)
            startActivity(intent)
        }
    }
}