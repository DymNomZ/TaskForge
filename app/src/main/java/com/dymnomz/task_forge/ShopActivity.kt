package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.dymnomz.task_forge.data.Consumable
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.helper.CustomListAdapterItem

class ShopActivity : Activity() {
    companion object {
        var lorem = "Dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

        val gears = mutableListOf(
            Gear("Helmets", 10, lorem, R.drawable.dymes),
            Gear("Armor", 10, lorem, R.drawable.dymes),
            Gear("Left Hand Item", 10, lorem, R.drawable.dymes),
            Gear("Right Hand Item", 10, lorem, R.drawable.dymes),
            Gear("Eyewear", 10, lorem, R.drawable.dymes),
            Gear("Back Item", 10, lorem, R.drawable.dymes),
        )

        val consumables = mutableListOf(
            Consumable("Potions", 10, lorem, R.drawable.dymes),
            Consumable("Meats", 10, lorem, R.drawable.dymes),
            Consumable("Fruits", 10, lorem, R.drawable.dymes),
            Consumable("Vegetables", 10, lorem, R.drawable.dymes),
            Consumable("Drinks", 10, lorem, R.drawable.dymes),
            Consumable("Snacks", 10, lorem, R.drawable.dymes),
        )
    }

    lateinit var gearsListView: ListView
    lateinit var consumablesListView: ListView
    lateinit var gearsAdapter: CustomListAdapterItem
    lateinit var consumablesAdapter: CustomListAdapterItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        gearsListView = findViewById<ListView>(R.id.gears_list)
        consumablesListView = findViewById<ListView>(R.id.consumables_list)

        gearsAdapter = CustomListAdapterItem(
            this, gears,
            onClick = {item, position ->

            }
        )

        consumablesAdapter = CustomListAdapterItem(
            this, consumables,
            onClick = {item, position ->

            }
        )

        gearsListView.adapter = gearsAdapter
        consumablesListView.adapter = consumablesAdapter

        val ToQuestsButton = findViewById<Button>(R.id.to_quests_btn)
        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToMenuButton = findViewById<Button>(R.id.to_menu_btn)

        ToQuestsButton.setOnClickListener {
            val intent = Intent(this, QuestsActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToInventoryButton.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToTasksButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToMenuButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        gearsAdapter.notifyDataSetChanged()
        consumablesAdapter.notifyDataSetChanged()
    }
}