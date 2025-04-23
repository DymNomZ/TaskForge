package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.min
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var PlayernameET = findViewById<EditText>(R.id.playername_et)
        var EmailET = findViewById<EditText>(R.id.email_et)
        var PasswordET = findViewById<EditText>(R.id.password_et)
        var SaveButton = findViewById<Button>(R.id.save_btn)
        var CancelButton = findViewById<Button>(R.id.cancel_btn)
        val ProfilePic = findViewById<ImageView>(R.id.profile_pic)

        var username = (application as UserData).username
        var playername = (application as UserData).playername
        var email = (application as UserData).email
        var password = (application as UserData).password

        PlayernameET.setText(playername)
        EmailET.setText(email)
        PasswordET.setText(password)

        var mainLayout = findViewById<LinearLayout>(R.id.main)

        var userPrefsManager = UserPreferenceManager(this)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
        }

        val userPicture = File(applicationContext.filesDir, username + "_profile_picture.png")
        if (userPicture.exists()) {
            ProfilePic.setImageBitmap(BitmapFactory.decodeFile(userPicture.path))
        }

        ProfilePic.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.setType("image/*")
            startActivityForResult(photoPickerIntent, 2)
        }

        CancelButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        SaveButton.setOnClickListener {

            val newProfile = File(applicationContext.filesDir, username + "_temp_profile_picture.png")
            if (newProfile.exists()) {
                if (userPicture.exists()) {
                    userPicture.delete()
                }
                newProfile.renameTo(userPicture)
            }

            playername = PlayernameET.text.toString()
            email = EmailET.text.toString()
            password = PasswordET.text.toString()

            //only proceed if all edit texts are filled with values
            if (playername.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()
            ) {

                //save to device
                var userPrefsManager = UserPreferenceManager(this)
                var username = (application as UserData).username
                userPrefsManager.saveUser(username, email, password, playername)

                //update UserData
                (application as UserData).playername = playername
                (application as UserData).email = email
                (application as UserData).password = password

                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Must input all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream = imageUri?.let { contentResolver.openInputStream(it) }
                var selectedImage = BitmapFactory.decodeStream(imageStream)

                val maxDimension = min(selectedImage.width, selectedImage.height)
                var x = 0
                var y = 0
                if (selectedImage.width > maxDimension) {
                    x = (selectedImage.width - maxDimension) / 2
                }
                if (selectedImage.height > maxDimension) {
                    y = (selectedImage.height - maxDimension) / 2
                }

                selectedImage = Bitmap.createBitmap(selectedImage, x, y, maxDimension, maxDimension)

                var username = (application as UserData).username

                val file = File(applicationContext.filesDir, username + "_temp_profile_picture.png")
                if (file.exists()) {
                    file.delete()
                }

                val out = FileOutputStream(file)
                selectedImage.compress(Bitmap.CompressFormat.PNG, 85, out)
                out.flush()
                out.close()

                findViewById<ImageView>(R.id.profile_pic).setImageBitmap(selectedImage)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
}