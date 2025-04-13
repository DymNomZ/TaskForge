package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

        var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var username = sp.getString("username", "")
        var email = sp.getString("email", "")
        var password = sp.getString("password", "")

        //save to UserData class
        (application as UserData).username = username!!
        (application as UserData).email = email!!
        (application as UserData).password = password!!

        //to check if there exists an account
        var hasAccount = false
        if(!username.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()){
            hasAccount = true
        }

        UsernameET.setText(username)
        PasswordET.setText(password)

        val LoginButton = findViewById<Button>(R.id.login_button)
        LoginButton.setOnClickListener {

            //since no account, inform the user
            if(!hasAccount){
                Toast.makeText(this, "No account created yet!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //only proceed if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty()){

                //validate
                if(UsernameET.text.toString() == username && PasswordET.text.toString() == password){
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