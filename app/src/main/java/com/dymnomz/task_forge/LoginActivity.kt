package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {

    lateinit var UsernameET : EditText
    lateinit var EmailET : EditText
    lateinit var BirthdayET : EditText
    lateinit var AddressET : EditText
    lateinit var PasswordET : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        UsernameET = findViewById<EditText>(R.id.username_et)
        EmailET = findViewById<EditText>(R.id.email_et)
        BirthdayET = findViewById<EditText>(R.id.birthday_et)
        AddressET = findViewById<EditText>(R.id.address_et)
        PasswordET = findViewById<EditText>(R.id.password_et)

        val LoginButton = findViewById<Button>(R.id.login_button)
        LoginButton.setOnClickListener {
            Log.e("LoginButton", "LoginButton is clicked")

            //only proceed if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && EmailET.text.toString().isNotEmpty() &&
                BirthdayET.text.toString().isNotEmpty() && AddressET.text.toString().isNotEmpty() &&
                PasswordET.text.toString().isNotEmpty()){
                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)

                User.username = UsernameET.text.toString()
                User.email = EmailET.text.toString()
                User.birthday = BirthdayET.text.toString()
                User.address = AddressET.text.toString()
            }
            else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }

        }



    }
}