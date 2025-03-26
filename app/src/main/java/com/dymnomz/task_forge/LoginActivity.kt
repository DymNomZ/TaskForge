package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val UsernameET = findViewById<EditText>(R.id.username_et)
        val PasswordET = findViewById<EditText>(R.id.password_et)

        intent?.let {
            it.getStringExtra("username")?.let { username ->
                UsernameET.setText(username)
            }
            it.getStringExtra("password")?.let { password ->
                PasswordET.setText(password)
            }
        }

        val LoginButton = findViewById<Button>(R.id.login_button)
        LoginButton.setOnClickListener {

            //only proceed if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty()){

                var userdata = application as UserData

                //validate
                if(UsernameET.text.toString() == userdata.username && PasswordET.text.toString() == userdata.password){
                    val intent = Intent(this, TasksActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            }
            else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        val createAccountTextView = findViewById<TextView>(R.id.create_accountTV)
        createAccountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}