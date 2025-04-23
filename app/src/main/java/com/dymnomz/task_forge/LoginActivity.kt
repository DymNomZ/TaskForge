package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.EquipmentTracker
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.getCurrentDate

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val UsernameET = findViewById<EditText>(R.id.username_et)
        val PasswordET = findViewById<EditText>(R.id.password_et)

        intent?.let {
            it.getStringExtra("username")?.let { username->
                UsernameET.setText(username)
            }
            it.getStringExtra("password")?.let { password->
                PasswordET.setText(password)
            }
        }

        val LoginButton = findViewById<Button>(R.id.login_button)
        LoginButton.setOnClickListener {

            var username = UsernameET.text.toString().trimEnd()
            var password = PasswordET.text.toString().trimEnd()

            //get details via shared prefs
            val userPrefsManager = UserPreferenceManager(this)

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(!userPrefsManager.userExists(username)){
                Toast.makeText(this, "No such account, please register", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val userDetailsString = userPrefsManager.getUserDetailsString(username)
            val userDetailsInt = userPrefsManager.getUserDetailsInt(username)

            var userPassword = userDetailsString?.get("password") ?: ""

            if(password != userPassword){
                Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val date = getCurrentDate()

            userPrefsManager.logDate(username, date)

            //save to UserData class
            (application as UserData).username = userDetailsString?.get("username") ?: ""
            (application as UserData).email = userDetailsString?.get("email") ?: ""
            (application as UserData).password = userDetailsString?.get("password") ?: ""
            (application as UserData).playername = userDetailsString?.get("playername") ?: "Player"
            (application as UserData).hp = userDetailsInt?.get("hp") ?: 100
            (application as UserData).coins = userDetailsInt?.get("coins") ?: 0
            (application as UserData).level = userDetailsInt?.get("level") ?: 1
            (application as UserData).xp = userDetailsInt?.get("xp") ?: 0

            //get equipments
            EquipmentTracker.loadEquipment(this, username)

            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()

        }

        val createAccountTextView = findViewById<TextView>(R.id.create_accountTV)
        createAccountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}