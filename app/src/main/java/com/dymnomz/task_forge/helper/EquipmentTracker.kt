package com.dymnomz.task_forge.helper

import android.content.Context
import com.dymnomz.task_forge.R

class EquipmentTracker{

    companion object{

        private const val KEY_BACK = "backSelected"
        private const val KEY_EYE = "eyeSelected"
        private const val KEY_LEFT = "leftSelected"
        private const val KEY_RIGHT = "rightSelected"
        private const val KEY_HEAD = "headSelected"
        private const val KEY_BODY = "bodySelected"

        var backSelected = R.drawable.blank_large
        var eyeSelected = R.drawable.blank
        var leftSelected = R.drawable.blank
        var rightSelected = R.drawable.blank
        var headSelected = R.drawable.blank
        var bodySelected = R.drawable.blank

        fun resetEquipment(context: Context, username: String){
            backSelected = R.drawable.blank_large
            eyeSelected = R.drawable.blank
            leftSelected = R.drawable.blank
            rightSelected = R.drawable.blank
            headSelected = R.drawable.blank
            bodySelected = R.drawable.blank

            saveEquipment(context, username)
        }

        fun saveEquipment(context: Context, username: String) {
            val sharedPrefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putInt(KEY_BACK, backSelected)
            editor.putInt(KEY_EYE, eyeSelected)
            editor.putInt(KEY_LEFT, leftSelected)
            editor.putInt(KEY_RIGHT, rightSelected)
            editor.putInt(KEY_HEAD, headSelected)
            editor.putInt(KEY_BODY, bodySelected)
            editor.commit()
        }
        fun loadEquipment(context: Context, username: String) {
            val sharedPrefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)
            backSelected = sharedPrefs.getInt(KEY_BACK, R.drawable.blank_large)
            eyeSelected = sharedPrefs.getInt(KEY_EYE, R.drawable.blank)
            leftSelected = sharedPrefs.getInt(KEY_LEFT, R.drawable.blank)
            rightSelected = sharedPrefs.getInt(KEY_RIGHT, R.drawable.blank)
            headSelected = sharedPrefs.getInt(KEY_HEAD, R.drawable.blank)
            bodySelected = sharedPrefs.getInt(KEY_BODY, R.drawable.blank)
        }

    }

}