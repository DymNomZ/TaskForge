package com.dymnomz.task_forge.app

import android.app.Application

class UserData : Application() {

    var username: String = ""
    var email: String = ""
    var password: String = ""

    fun isUserDataEmpty(): Boolean {
        return username.isNullOrEmpty() && email.isNullOrEmpty() && password.isNullOrEmpty()
    }

    override fun onCreate() {
        super.onCreate()
    }
}