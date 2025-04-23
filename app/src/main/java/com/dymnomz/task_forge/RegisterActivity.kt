package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.getCurrentDate

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var mainLayout = findViewById<LinearLayout>(R.id.main)

        var logo = findViewById<ImageView>(R.id.logo)
        var taskForge = findViewById<TextView>(R.id.task_forge)

        var userPrefsManager = UserPreferenceManager(this)
        var username = (application as UserData).username

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
            logo.setImageResource(R.drawable.logo_colored)
            taskForge.setTextColor(ContextCompat.getColor(this, R.color.orange_1))
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
            logo.setImageResource(R.drawable.logo_white)
            taskForge.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

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