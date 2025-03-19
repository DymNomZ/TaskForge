package com.dymnomz.task_forge.app

import android.app.Application

class UserData : Application() {

    var username: String = ""
    var email: String = ""
    var password: String = ""
    var birthday: String = ""

    override fun onCreate() {
        super.onCreate()
    }
}