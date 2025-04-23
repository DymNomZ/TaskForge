package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var mainLayout = findViewById<LinearLayout>(R.id.main)
        var logo = findViewById<ImageView>(R.id.logo)
        var taskForge = findViewById<TextView>(R.id.task_forge)

        var userPrefsManager = UserPreferenceManager(this)
        var username = (application as UserData).username

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
            logo.setImageResource(R.drawable.logo_colored)
            taskForge.setTextColor(ContextCompat.getColor(this, R.color.orange_1))
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
            logo.setImageResource(R.drawable.logo_white)
            taskForge.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}