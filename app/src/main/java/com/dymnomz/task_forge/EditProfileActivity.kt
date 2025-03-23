package com.dymnomz.task_forge

import android.app.Activity
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

        var userdata = application as UserData

//        if(!userdata.isUserDataEmpty()){
//            UsernameET.setText(userdata.username)
//            EmailET.setText(userdata.email)
//            PasswordET.setText(userdata.password)
//        }

        CancelButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        SaveButton.setOnClickListener {

            //only proceed if all edit texts are filled with values
            if (UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty()
                && EmailET.text.toString().isNotEmpty()
            ) {

                //save to UserData class
//                userdata.username = UsernameET.text.toString()
//                userdata.email = EmailET.text.toString()
//                userdata.password = PasswordET.text.toString()

                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}