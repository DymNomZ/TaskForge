package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ProfileActivity : Activity() {

    lateinit var UsernameET : EditText
    lateinit var EmailET : EditText
    lateinit var BirthdayET : EditText
    lateinit var AddressET : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        UsernameET = findViewById<EditText>(R.id.username_et)
        EmailET = findViewById<EditText>(R.id.email_et)
        BirthdayET = findViewById<EditText>(R.id.birthday_et)
        AddressET = findViewById<EditText>(R.id.address_et)

        UsernameET.setText(User.username)
        EmailET.setText(User.email)
        BirthdayET.setText(User.birthday)
        AddressET.setText(User.address)

        saveInfo()

        val ToLandingButton = findViewById<Button>(R.id.to_landing_btn)
        ToLandingButton.setOnClickListener {
            Log.e("ToLandingButton", "ToLandingButton is clicked")

            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }

        val ToSettingsButton = findViewById<Button>(R.id.to_settings_btn)
        ToSettingsButton.setOnClickListener {
            Log.e("ToSettingsButton", "ToSettingsButton is clicked")

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val SaveProfileButton = findViewById<Button>(R.id.save_profile_btn)
        SaveProfileButton.setOnClickListener {
            Log.e("SaveProfileButton", "SaveProfileButton is clicked")

            //only save if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && EmailET.text.toString().isNotEmpty() &&
                BirthdayET.text.toString().isNotEmpty() && AddressET.text.toString().isNotEmpty()){
                saveInfo()
                Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveInfo() {
        User.username = UsernameET.text.toString()
        User.email = EmailET.text.toString()
        User.birthday = BirthdayET.text.toString()
        User.address = AddressET.text.toString()
    }
}