package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.data.Consumable
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.helper.CustomListAdapterInventory
import com.dymnomz.task_forge.helper.CustomListAdapterItem
import com.dymnomz.task_forge.helper.getConsumablesFromDevice
import com.dymnomz.task_forge.helper.getGearsFromDevice
import com.dymnomz.task_forge.helper.saveConsumablesToDevice
import com.dymnomz.task_forge.helper.saveGearsToDevice
import com.dymnomz.task_forge.helper.showBasicDialogue
import com.dymnomz.task_forge.helper.showInventoryItemDialogue

class InventoryActivity : Activity() {

    companion object {
        var gears : MutableList<Gear> = mutableListOf()
        var consumables : MutableList<Consumable> = mutableListOf()
    }

    lateinit var gearsListView: ListView
    lateinit var consumablesListView: ListView
    lateinit var gearsAdapter: CustomListAdapterInventory
    lateinit var consumablesAdapter: CustomListAdapterInventory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        gearsListView = findViewById<ListView>(R.id.gears_list)
        consumablesListView = findViewById<ListView>(R.id.consumables_list)

        gears = getGearsFromDevice(this, "inventory_gears")
        consumables = getConsumablesFromDevice(this, "inventory_consumables")

        gearsAdapter = CustomListAdapterInventory(
            this, gears,
            onClick = {item, position ->
                showInventoryItemDialogue(
                    this, item, "Equip",
                    onAction = {
                              //logic
                    },
                    onDiscard = {
                        gears.removeAt(position)
                        saveGearsToDevice(this, "user_tasks", gears)
                        onResume()
                    }
                )
            }
        )

        consumablesAdapter = CustomListAdapterInventory(
            this, consumables,
            onClick = {item, position ->
                showInventoryItemDialogue(
                    this, item, "Consume",
                    onAction = {
                        //check if player health is less than 100
                        if((application as UserData).hp < 100){

                            (application as UserData).hp += 10

                            //check if over 100
                            if((application as UserData).hp > 100){
                                (application as UserData).hp = 100
                            }

                            var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            var hp = (application as UserData).hp
                            var editor = sp.edit()
                            editor.putInt("hp", hp)
                            editor.commit()

                            Toast.makeText(this, "Restored player health by 10hp!!", Toast.LENGTH_SHORT).show()

                            consumables.removeAt(position)
                            saveConsumablesToDevice(this, "user_tasks", consumables)
                            onResume()
                        }
                        else{
                            Toast.makeText(this, "Player health is full!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onDiscard = {
                        consumables.removeAt(position)
                        saveConsumablesToDevice(this, "user_tasks", consumables)
                        onResume()
                    }
                )
            }
        )

        gearsListView.adapter = gearsAdapter
        consumablesListView.adapter = consumablesAdapter

        val ToQuestsButton = findViewById<Button>(R.id.to_quests_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val ToProfileButton = findViewById<Button>(R.id.to_menu_btn)

        ToQuestsButton.setOnClickListener {
            val intent = Intent(this, QuestsActivity::class.java)
            startActivity(intent)
            finish()
        }

        ToTasksButton.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
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
        gearsAdapter.notifyDataSetChanged()
        consumablesAdapter.notifyDataSetChanged()
    }
}