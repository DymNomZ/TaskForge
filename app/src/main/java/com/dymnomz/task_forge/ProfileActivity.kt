package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class ProfileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val ToQuestsButton = findViewById<Button>(R.id.to_quests_btn)
        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val SettingsButton = findViewById<ImageButton>(R.id.settings_btn)
        val EditButton = findViewById<Button>(R.id.edit_btn)
        val PetsButton = findViewById<Button>(R.id.pets_btn)
        val NicknameET = findViewById<EditText>(R.id.nickname_et)
        val UsernameTV = findViewById<TextView>(R.id.username_tv)
        var MemberSinceTV = findViewById<TextView>(R.id.member_since_tv)
        var LastLoggedTV = findViewById<TextView>(R.id.last_log_tv)

        var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var username = sp.getString("username", "")
        var memeber_since_date = sp.getString("creation_date", "")
        var last_logged_in_Date = sp.getString("logged_in_date", "")

        UsernameTV.setText("@" + username)
        MemberSinceTV.setText(memeber_since_date)
        LastLoggedTV.setText(last_logged_in_Date)

        NicknameET.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                //sample lpgic
                val text = textView.text.toString()
                println("Entered text: $text")
                Log.i("YourTag", "Activity created.")
                true
            } else {
                false
            }
        }

        EditButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        PetsButton.setOnClickListener {
            val intent = Intent(this, PetsActivity::class.java)
            startActivity(intent)
            finish()
        }

        SettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToQuestsButton.setOnClickListener {
            val intent = Intent(this, QuestsActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToInventoryButton.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToTasksButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToShopButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}