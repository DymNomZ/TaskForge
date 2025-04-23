package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.getCurrentDate

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

            val username = UsernameET.text.toString().trimEnd()
            val email = EmailET.text.toString().trimEnd()
            val password = PasswordET.text.toString().trimEnd()
            val confirmpassword = ConfirmPasswordET.text.toString().trimEnd()

            if(username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(!email.contains("@")){
                Toast.makeText(this, "Not a valid email address", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password != confirmpassword){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val date = getCurrentDate()

            //save to device
            val userPrefsManager = UserPreferenceManager(this)

            if(!userPrefsManager.userExists(username)){

                (application as UserData).username = username
                (application as UserData).email = email
                (application as UserData).password = password

                userPrefsManager.addUser(username, email, password, date)

                val intent = Intent(this, LoginActivity::class.java).apply{
                    putExtra("username", username)
                    putExtra("password", password)
                }
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Username is taken", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

        }
    }
}