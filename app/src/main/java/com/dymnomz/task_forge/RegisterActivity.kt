package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

            //only proceed if all edit texts are filled with values
            if(UsernameET.text.toString().isNotEmpty() && PasswordET.text.toString().isNotEmpty() &&
                ConfirmPasswordET.text.toString().isNotEmpty() && EmailET.text.toString().isNotEmpty()){

                var username = UsernameET.text.toString()
                var email = EmailET.text.toString()
                var password = PasswordET.text.toString()

                //save to device
                var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                var editor = sp.edit();

                val dateInString = getCurrentDate()

                editor.putString("username", username)
                editor.putString("email", email)
                editor.putString("password", password)
                editor.putString("creation_date", dateInString)
                editor.commit()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
            else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}