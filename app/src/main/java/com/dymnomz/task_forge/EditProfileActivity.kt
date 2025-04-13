package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData

class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var UsernameET = findViewById<EditText>(R.id.username_et)
        var EmailET = findViewById<EditText>(R.id.email_et)
        var PasswordET = findViewById<EditText>(R.id.password_et)
        var SaveButton = findViewById<Button>(R.id.save_btn)
        var CancelButton = findViewById<Button>(R.id.cancel_btn)

        var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var username = sp.getString("username", "")
        var email = sp.getString("email", "")
        var password = sp.getString("password", "")

        UsernameET.setText(username)
        EmailET.setText(email)
        PasswordET.setText(password)


        CancelButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        SaveButton.setOnClickListener {

            //only proceed if all edit texts are filled with values
            if (UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty()
                && EmailET.text.toString().isNotEmpty()
            ) {

                //save to UserData class
//                (application as UserData).username = UsernameET.text.toString()
//                (application as UserData).email = EmailET.text.toString()
//                (application as UserData).password = PasswordET.text.toString()

                //save to device
                var editor = sp.edit();
                username = UsernameET.text.toString()
                email = EmailET.text.toString()
                password = PasswordET.text.toString()

                editor.putString("username", username)
                editor.putString("email", email)
                editor.putString("password", password)
                editor.commit()

                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}