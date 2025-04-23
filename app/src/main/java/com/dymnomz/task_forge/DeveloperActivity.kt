package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager

class DeveloperActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        var mainLayout = findViewById<LinearLayout>(R.id.main)

        var userPrefsManager = UserPreferenceManager(this)
        var username = (application as UserData).username

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
        }

        var CloseButton = findViewById<ImageButton>(R.id.close_btn)

        CloseButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}