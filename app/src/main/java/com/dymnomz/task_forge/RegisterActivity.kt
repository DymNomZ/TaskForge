package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dymnomz.task_forge.app.UserData

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val UsernameET = findViewById<EditText>(R.id.username_et)
        val PasswordET = findViewById<EditText>(R.id.password_et)
        val BirthdayET = findViewById<EditText>(R.id.birthday_et)
        val EmailET = findViewById<EditText>(R.id.email_et)

        val RegisterButton = findViewById<Button>(R.id.register_button)
        RegisterButton.setOnClickListener {

            //only proceed if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty() &&
                BirthdayET.text.toString().isNotEmpty() && EmailET.text.toString().isNotEmpty()){

                var userdata = application as UserData

                //save to UserData class
                userdata.username = UsernameET.text.toString()
                userdata.email = EmailET.text.toString()
                userdata.password = PasswordET.text.toString()
                userdata.birthday = BirthdayET.text.toString()

                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("username", userdata.username)
                    putExtra("password", userdata.password)
                }
                startActivity(intent)

            }
            else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}