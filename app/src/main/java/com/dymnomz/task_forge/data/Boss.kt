package com.dymnomz.task_forge.data

import com.dymnomz.task_forge.R

class Boss(
    var name: String = "",
    var desc: String = "",
    var hp: Int = 100,
    var damage: Int = 10,
    var required_level: Int = 1,
    var img: Int = R.drawable.dymes
){

    fun resetValues(){
        hp = 100
    }

    fun checkIfDead(): Boolean{
        if(hp <= 0 && name.isNotEmpty()) return true
        return false
    }

    fun getDamage(damage: Int){
        if(!checkIfDead()) hp -= damage
    }

    fun doDamage(): Int {
        return damage;
    }
}