package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.dymnomz.task_forge.helper.showCustomDialogue

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var AboutTheDevButton = findViewById<Button>(R.id.developer_btn)
        var ToggleDarkModeButton = findViewById<Button>(R.id.dark_mode_btn)
        var ResetProgressButton = findViewById<Button>(R.id.reset_btn)
        var DeleteAccButton = findViewById<Button>(R.id.delete_acc_btn)
        var LogoutButton = findViewById<Button>(R.id.logout_btn)
        var BackButton = findViewById<ImageButton>(R.id.back_btn)

        BackButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        AboutTheDevButton.setOnClickListener {
            val intent = Intent(this, DeveloperActivity::class.java)
            startActivity(intent)
            finish()
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
            showCustomDialogue(
             this,
             "Confirm Logout",
             "Are you sure you want to logout?",
             onConfirm = {
                 val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                 finish()
             }
         )

        }
    }
}