package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class QuestsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quests)
        val questList = listOf("The Daily Forge", "Chronicles of the Steadfast", "The Aetherium Grind", "Summit of Self-Mastery", "Echoes of Efficiency", "The Luminary Path")

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            questList
        )

        val listView = findViewById<ListView>(R.id.quests_list)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                this,
                "Item $position with data ${questList[position]}",
                Toast.LENGTH_LONG
            ).show()
        }


        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val ToProfileButton = findViewById<Button>(R.id.to_menu_btn)

        ToInventoryButton.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
        }

        ToTasksButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
        }

        ToShopButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

        ToProfileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}