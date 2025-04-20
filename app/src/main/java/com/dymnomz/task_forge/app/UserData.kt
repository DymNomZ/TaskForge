package com.dymnomz.task_forge.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class UserData : Application() {

    var username: String = ""
    var email: String = ""
    var password: String = ""
    var playername: String = ""
    var hp: Int = 0
    var coins: Int = 0
    var level: Int = 0
    var xp: Int = 0
    override fun onCreate() {
        super.onCreate()
    }
}