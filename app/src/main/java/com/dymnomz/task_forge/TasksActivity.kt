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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val listView =findViewById<ListView>(R.id.tasks_list)

        val tasks = listOf(
            Task("Conquer the Inbox Beast", R.drawable.dymes),
            Task("Forge a Daily Habit", R.drawable.dymes),
            Task("Slay the Procrastination Dragon", R.drawable.dymes),
            Task("Craft a Masterpiece", R.drawable.dymes),
            Task("Journey to Fitness Peak", R.drawable.dymes),
            Task("Unearth Hidden Knowledge", R.drawable.dymes),
            Task("Tame the To-Do List", R.drawable.dymes),
            Task("Cultivate Inner Peace", R.drawable.dymes),
            Task("Build a Social Stronghold", R.drawable.dymes),
            Task("Master the Culinary Arts", R.drawable.dymes),
            Task("Chart a Financial Course", R.drawable.dymes),
            Task("Repair the Broken Bridge", R.drawable.dymes),
            Task("Explore the Wild Unknown", R.drawable.dymes)
        )

        val adapter = CustomListAdapterTask(
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
        }

        ToQuestsButton.setOnClickListener {
            val intent = Intent(this, QuestsActivity::class.java)
            startActivity(intent)
        }

        ToInventoryButton.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
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