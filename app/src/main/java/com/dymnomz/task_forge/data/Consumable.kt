package com.dymnomz.task_forge.data

class Consumable(
    name: String,
    cost: Int,
    desc: String,
    img: Int,
    type: String
) : Item(name, cost, desc, img, type)