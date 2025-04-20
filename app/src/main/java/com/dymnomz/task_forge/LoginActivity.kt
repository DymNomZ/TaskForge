package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.EquipmentTracker
import com.dymnomz.task_forge.helper.getCurrentDate

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val UsernameET = findViewById<EditText>(R.id.username_et)
        val PasswordET = findViewById<EditText>(R.id.password_et)

        val dateInString = getCurrentDate()

        var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var username = sp.getString("username", "")
        var email = sp.getString("email", "")
        var password = sp.getString("password", "")
        var hp = sp.getInt("hp", 100)
        var coins = sp.getInt("coins", 100)
        var level = sp.getInt("level", 1)
        var xp = sp.getInt("xp", 0)

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

                    var editor = sp.edit();
                    editor.putString("logged_in_date", dateInString)
                    editor.commit()

                    //save to UserData class
                    (application as UserData).username = username!!
                    (application as UserData).email = email!!
                    (application as UserData).password = password!!
                    (application as UserData).hp = hp!!
                    (application as UserData).coins = coins!!
                    (application as UserData).level = level!!
                    (application as UserData).xp = xp!!

                    //get equipments
                    EquipmentTracker.loadEquipment(this)

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