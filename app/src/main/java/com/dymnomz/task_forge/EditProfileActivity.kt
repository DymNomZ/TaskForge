package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager

class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var PlayernameET = findViewById<EditText>(R.id.playername_et)
        var EmailET = findViewById<EditText>(R.id.email_et)
        var PasswordET = findViewById<EditText>(R.id.password_et)
        var SaveButton = findViewById<Button>(R.id.save_btn)
        var CancelButton = findViewById<Button>(R.id.cancel_btn)

        var playername = (application as UserData).playername
        var email = (application as UserData).email
        var password = (application as UserData).username

        PlayernameET.setText(playername)
        EmailET.setText(email)
        PasswordET.setText(password)


        CancelButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        SaveButton.setOnClickListener {

            playername = PlayernameET.text.toString()
            email = EmailET.text.toString()
            password = PasswordET.text.toString()

            //only proceed if all edit texts are filled with values
            if (playername.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()
            ) {

                //save to device
                var userPrefsManager = UserPreferenceManager(this)
                var username = (application as UserData).username
                userPrefsManager.saveUser(username, email, password)

                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}