package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager
import java.io.File

class ProfileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val ToBossesButton = findViewById<Button>(R.id.to_bosses_btn)
        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val SettingsButton = findViewById<ImageButton>(R.id.settings_btn)
        val EditButton = findViewById<Button>(R.id.edit_btn)
        val PlayernameTV = findViewById<TextView>(R.id.playername_tv)
        val UsernameTV = findViewById<TextView>(R.id.username_tv)
        val ProfilePic = findViewById<ImageView>(R.id.profile_pic)

        var MemberSinceTV = findViewById<TextView>(R.id.member_since_tv)
        var LastLoggedTV = findViewById<TextView>(R.id.last_log_tv)

        var userPrefsManager = UserPreferenceManager(this)
        var username = (application as UserData).username
        var userDetails = userPrefsManager.getUserDetailsString(username)

        var mainLayout = findViewById<LinearLayout>(R.id.main)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
        }

        var playername = userDetails?.get("playername") ?: "Player"
        var memeber_since_date = userDetails?.get("creation_date") ?: ""
        var last_logged_in_Date = userDetails?.get("logged_in_date") ?: ""

        val userPicture = File(applicationContext.filesDir, username + "_profile_picture.png")
        if (userPicture.exists()) {
            ProfilePic.setImageBitmap(BitmapFactory.decodeFile(userPicture.path))
        }

        PlayernameTV.setText(playername)
        UsernameTV.setText("@" + username)
        MemberSinceTV.setText(memeber_since_date)
        LastLoggedTV.setText(last_logged_in_Date)

        EditButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        SettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            ToBossesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToShopButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToInventoryButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
        } else {
            ToBossesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToShopButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToInventoryButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
        }

        ToBossesButton.setOnClickListener {
            val intent = Intent(this, BossesActivity::class.java)
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