package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton

class PetsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets)

        var BackButton = findViewById<ImageButton>(R.id.back_btn)
        var PetNameET = findViewById<EditText>(R.id.pet_name_et)

        BackButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        PetNameET.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                //sample lpgic
                val text = textView.text.toString()
                println("Entered text: $text")
                true
            } else {
                false
            }
        }
    }
}