package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.dymnomz.task_forge.data.Task
import com.dymnomz.task_forge.helper.CustomListAdapterTask

class TasksActivity : Activity() {

    companion object {
        val tasks = mutableListOf(
            Task("Conquer the Inbox Beast", "April 24, 2025", "Easy"),
            Task("Forge a Daily Habit", "April 24, 2025", "Easy"),
            Task("Slay the Procrastination Dragon","April 24, 2025", "Easy"),
            Task("Craft a Masterpiece","April 24, 2025", "Easy"),
            Task("Journey to Fitness Peak", "April 24, 2025", "Easy"),
            Task("Unearth Hidden Knowledge", "April 24, 2025", "Easy"),
            Task("Tame the To-Do List", "April 24, 2025", "Easy"),
            Task("Cultivate Inner Peace", "April 24, 2025", "Easy"),
            Task("Build a Social Stronghold", "April 24, 2025", "Easy"),
            Task("Master the Culinary Arts", "April 24, 2025", "Easy"),
            Task("Chart a Financial Course", "April 24, 2025", "Easy"),
            Task("Repair the Broken Bridge", "April 24, 2025", "Easy"),
            Task("Explore the Wild Unknown", "April 24, 2025", "Easy"),
        )
    }

    lateinit var listView: ListView
    lateinit var adapter: CustomListAdapterTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        listView = findViewById<ListView>(R.id.tasks_list)

        adapter = CustomListAdapterTask(
            this, tasks,
            onClick = {task, position ->
                Toast.makeText(
                    this,
                    "clicked on task",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
        listView.adapter = adapter

        val ToQuestsButton = findViewById<Button>(R.id.to_quests_btn)
        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val ToProfileButton = findViewById<Button>(R.id.to_menu_btn)
        val CreateTaskButton = findViewById<ImageButton>(R.id.create_task_btn)

        CreateTaskButton.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
            finish()
        }

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

        ToShopButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToProfileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}