package com.dymnomz.task_forge.data

class Gear(
    name: String,
    cost: Int,
    desc: String,
    img: Int,
    type: String,
    var isEquipped: Boolean
) : Item(name, cost, desc, img, type)