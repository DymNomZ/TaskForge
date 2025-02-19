package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton

class DeveloperActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        val BackButton = findViewById<ImageButton>(R.id.back_btn)
        BackButton.setOnClickListener {
            Log.e("BackButton", "BackButton is clicked")

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}