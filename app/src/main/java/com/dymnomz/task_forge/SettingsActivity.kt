package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.EquipmentTracker
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.showBasicDialogue

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

            showBasicDialogue(
                this,
                "Start over?",
                "This action cannot be undone. Are you sure?",
                "Confirm",
                onConfirm = {

                    var userPrefsManager = UserPreferenceManager(this)
                    var username = (application as UserData).username

                    //reset logic
                    (application as UserData).hp = 100
                    (application as UserData).coins = 0
                    (application as UserData).level = 1
                    (application as UserData).xp = 0

                    userPrefsManager.updatePlayerData(this, username, 100, 0, 1, 0)
                    userPrefsManager.saveTasksToDevice(this, username, mutableListOf())
                    userPrefsManager.saveGearsToDevice(this, username, "inventory_gears", mutableListOf())
                    userPrefsManager.saveConsumablesToDevice(this, username, "inventory_consumables", mutableListOf())

                    EquipmentTracker.resetEquipment(this, username)

                    val intent = Intent(this, TasksActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }

        DeleteAccButton.setOnClickListener {
            //logic here
        }

        LogoutButton.setOnClickListener {
            //popup
            showBasicDialogue(
             this,
             "Confirm Logout",
             "Are you sure you want to logout?",
                "Confirm",
             onConfirm = {
                 val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                 finish()
             }
         )

        }
    }
}