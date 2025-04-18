package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.data.Task
import com.dymnomz.task_forge.helper.CustomListAdapterTask
import com.dymnomz.task_forge.helper.getTasksFromDevice
import com.dymnomz.task_forge.helper.saveTasksToDevice
import com.dymnomz.task_forge.helper.showBasicDialogue

class TasksActivity : Activity() {

    companion object {
        var tasks : MutableList<Task> = mutableListOf()
    }

    lateinit var listView: ListView
    lateinit var adapter: CustomListAdapterTask
    lateinit var HPTV: TextView
    lateinit var CoinsTV: TextView
    lateinit var LevelTV: TextView

    fun updatePlayerValues(){

        var hp = (application as UserData).hp.toString()
        var coins = (application as UserData).coins.toString()
        var level = (application as UserData).level.toString()

        HPTV.setText(hp)
        CoinsTV.setText(coins)
        LevelTV.setText("Level " + level)
    }

    fun awardPlayer(){

        (application as UserData).coins += 10
        (application as UserData).xp += 10

        //check xp
        if((application as UserData).xp >= 100){
            (application as UserData).xp = 0;
            (application as UserData).level += 1
        }

        var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var hp = (application as UserData).hp
        var coins = (application as UserData).coins
        var level = (application as UserData).level
        var xp = (application as UserData).xp

        var editor = sp.edit()
        editor.putInt("hp", hp)
        editor.putInt("coins", coins)
        editor.putInt("level", level)
        editor.putInt("xp", xp)
        editor.commit()

        //update values
        updatePlayerValues()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        listView = findViewById<ListView>(R.id.tasks_list)

        tasks = getTasksFromDevice(this, "user_tasks")

        HPTV = findViewById<TextView>(R.id.hp_tv)
        CoinsTV = findViewById<TextView>(R.id.coins_tv)
        LevelTV = findViewById<TextView>(R.id.level_tv)

        updatePlayerValues()

        adapter = CustomListAdapterTask(
            this, tasks,
            onClick = {task, position ->

                var intent = Intent(this, EditTaskActivity::class.java).apply{
                    putExtra("difficulty", task.difficulty)
                    putExtra("title", task.title)
                }
                startActivity(intent)
                finish()
            },
            completeTask = {task, position ->

                awardPlayer()

                Toast.makeText(this, "Player earnings logic here!", Toast.LENGTH_SHORT).show()
                tasks.removeAt(position)
                saveTasksToDevice(this, "user_tasks", tasks)
                onResume()
            },
            deleteTask = {task, position ->
                showBasicDialogue(
                    this,
                    "Delete Task",
                    "Are you sure you want to delete this task?",
                    "Confirm",
                    onConfirm = {
                        tasks.removeAt(position)
                        saveTasksToDevice(this, "user_tasks", tasks)
                        onResume()
                    }
                )
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