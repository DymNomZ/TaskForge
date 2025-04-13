package com.dymnomz.task_forge.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class UserData : Application() {

    var username: String = ""
    var email: String = ""
    var password: String = ""
    override fun onCreate() {
        super.onCreate()
    }
}