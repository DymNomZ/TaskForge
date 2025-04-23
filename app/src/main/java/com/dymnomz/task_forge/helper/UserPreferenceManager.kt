package com.dymnomz.task_forge.helper

import android.content.Context
import android.widget.Toast
import com.dymnomz.task_forge.BossesActivity
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Boss
import com.dymnomz.task_forge.data.Consumable
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.data.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class UserPreferenceManager(private val context: Context) {

    fun userExists(username: String): Boolean {
        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        return sp.contains("username")
    }

    fun saveSelectedBossToDevice(context: Context, username: String, boss: Boss){
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val bossJson = gson.toJson(boss)
        val key = "${username}_selected_boss"
        editor.putString(key, bossJson)
        editor.commit()
    }

    fun getSelectedBossFromDevice(context: Context, username: String): Boss {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val gson = Gson()
        val key = "${username}_selected_boss"
        val bossJson = sharedPreferences.getString(key, null) // Use null as default
        return if (bossJson != null) {
            try {
                gson.fromJson(bossJson, Boss::class.java)
            } catch (e: Exception) {
                // Handle JSON parsing error, e.g., log it and return null
                e.printStackTrace()
                BossesActivity.blankBoss
            }
        } else {
            BossesActivity.blankBoss
        }
    }

    fun saveBossesToDevice(context: Context, username: String, bosses: MutableList<Boss>) {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonString = gson.toJson(bosses)
        editor.putString("user_bosses", jsonString)
        editor.commit()
    }

    fun getBossesFromDevice(context: Context, username: String): MutableList<Boss> {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("user_bosses", null)
        if (jsonString != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Boss>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf(
            Boss("Kaido", BossesActivity.lorem, 100, 10, 1, R.drawable.kaido),
            Boss("Kaido", BossesActivity.lorem, 200, 10, 5, R.drawable.kaido),
            Boss("Kaido", BossesActivity.lorem, 300, 10, 10, R.drawable.kaido),
            Boss("Kaido", BossesActivity.lorem, 400, 10, 15, R.drawable.kaido),
            Boss("Kaido", BossesActivity.lorem, 500, 10, 20, R.drawable.kaido),
            Boss("Kaido", BossesActivity.lorem, 600, 10, 25, R.drawable.kaido),
            Boss("Kaido", BossesActivity.lorem, 700, 10, 30, R.drawable.kaido)
        )

    }

    fun saveTasksToDevice(context: Context, username: String, gears: MutableList<Task>) {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonString = gson.toJson(gears)
        editor.putString("user_tasks", jsonString)
        editor.commit()
    }

    fun getTasksFromDevice(context: Context, username: String): MutableList<Task> {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("user_tasks", null)
        if (jsonString != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Task>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf() // Return an empty list if no data is found
    }

    fun saveGearsToDevice(context: Context, username: String, key: String, gears: MutableList<Gear>) {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonString = gson.toJson(gears)
        editor.putString(key, jsonString)
        editor.commit()
    }

    fun getGearsFromDevice(context: Context, username: String, key: String): MutableList<Gear> {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString(key, null)
        if (jsonString != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Gear>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf() // Return an empty list if no data is found
    }

    fun saveConsumablesToDevice(context: Context, username: String, key: String, gears: MutableList<Consumable>) {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonString = gson.toJson(gears)
        editor.putString(key, jsonString)
        editor.commit()
    }

    fun getConsumablesFromDevice(context: Context, username: String, key: String): MutableList<Consumable> {
        val sharedPreferences = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString(key, null)
        if (jsonString != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Consumable>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf() // Return an empty list if no data is found
    }

    fun getUserDetailsString(username: String): Map<String, String?>? {
        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        return if (sp.contains("username")) {
            mapOf(
                "username" to sp.getString("username", ""),
                "email" to sp.getString("email", ""),
                "password" to sp.getString("password", ""),
                "playername" to sp.getString("playername", "Player"),
                "creation_date" to sp.getString("creation_date", ""),
                "logged_in_date" to sp.getString("logged_in_date", "")
            )
        } else {
            null
        }
    }

    fun getUserDetailsInt(username: String): Map<String, Int?>? {
        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        return if (sp.contains("username")) {
            mapOf(
                "hp" to sp.getInt("hp", 100),
                "coins" to sp.getInt("coins", 0),
                "level" to sp.getInt("level", 1),
                "xp" to sp.getInt("hp", 0)
            )
        } else {
            null
        }
    }

    fun logDate(username: String, date: String){

        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("logged_in_date", date)
        editor.commit()

    }

    fun updatePlayerData(
        context: Context, username: String,
        hp: Int, coins: Int, level: Int, xp: Int
    ){

        var sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)

        var editor = sp.edit()
        editor.putInt("hp", hp)
        editor.putInt("coins", coins)
        editor.putInt("level", level)
        editor.putInt("xp", xp)
        editor.commit()
    }

    fun updateCoins(username: String, coins: Int){

        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt("coins", coins)
        editor.commit()

    }

    fun updateHealth(username: String, hp: Int){

        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt("hp", hp)
        editor.commit()

    }

    fun addUser(username: String, email: String, password: String, date: String) {

        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("playername", "Player")
        editor.putString("creation_date", date)
        editor.putInt("hp", 100)
        editor.putInt("coins", 0)
        editor.putInt("level", 1)
        editor.putInt("xp", 0)
        editor.commit()

        Toast.makeText(context, "$username added successfully", Toast.LENGTH_LONG).show()

    }

    fun saveUser(username: String, email: String, password: String, playername: String) {

        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("playername", playername)
        editor.commit()

        Toast.makeText(context, "Profile Saved", Toast.LENGTH_LONG).show()

    }

    fun removeUser(username: String) {
        val sp = context.getSharedPreferences(username, Context.MODE_PRIVATE)
        if (sp.contains("username")) {
            sp.edit().clear().commit()
            Toast.makeText(context, "$username removed successfully", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "$username does not exist", Toast.LENGTH_LONG).show()
        }
    }

}