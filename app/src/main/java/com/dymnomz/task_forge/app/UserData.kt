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

    fun checkLevel(){
        if(level <= 1){
            level = 1
        }
    }

    fun checkXP(){
        if(xp >= 100){
            xp = 0;
            level += 1
        }
        else if(xp <= 0){
            xp = 90;
            level -= 1
            checkLevel()
        }
    }

    fun checkCoins(){
        if(coins <= 0) coins = 0
    }

    fun makePurchase(cost: Int): Boolean{
        if(cost <= coins){
            coins -= cost
            return true
        }
        return false
    }

    fun forfeit(){
        xp -= 50
        coins -= 50
        checkXP()
        checkCoins()
    }

    fun award(){
        coins += 10
        xp += 10

        checkXP()
    }
    override fun onCreate() {
        super.onCreate()
    }
}