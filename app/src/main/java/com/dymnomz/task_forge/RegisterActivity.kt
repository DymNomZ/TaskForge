package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val UsernameET = findViewById<EditText>(R.id.username_et)
        val EmailET = findViewById<EditText>(R.id.email_et)
        val PasswordET = findViewById<EditText>(R.id.password_et)
        val ConfirmPasswordET = findViewById<EditText>(R.id.confirm_password_et)

        val RegisterButton = findViewById<Button>(R.id.register_button)
        RegisterButton.setOnClickListener {

            //only proceed if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty() &&
                ConfirmPasswordET.text.toString().isNotEmpty() && EmailET.text.toString().isNotEmpty()){

                //save to UserData class
                (application as UserData).username = UsernameET.text.toString()
                (application as UserData).email = EmailET.text.toString()
                (application as UserData).password = PasswordET.text.toString()

                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("username", (application as UserData).username)
                    putExtra("password", (application as UserData).password)
                }
                startActivity(intent)

            }
            else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}