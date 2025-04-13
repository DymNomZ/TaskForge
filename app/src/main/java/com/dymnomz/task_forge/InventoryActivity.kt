package com.dymnomz.task_forge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.dymnomz.task_forge.data.Consumable
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.helper.CustomListAdapterItem
import com.dymnomz.task_forge.helper.getConsumablesFromDevice
import com.dymnomz.task_forge.helper.getGearsFromDevice
import com.dymnomz.task_forge.helper.showBasicDialogue
import com.dymnomz.task_forge.helper.showPurchaseItemDialogue

class InventoryActivity : Activity() {

    companion object {
        var gears : MutableList<Gear> = mutableListOf()
        var consumables : MutableList<Consumable> = mutableListOf()
    }

    lateinit var gearsListView: ListView
    lateinit var consumablesListView: ListView
    lateinit var gearsAdapter: CustomListAdapterItem
    lateinit var consumablesAdapter: CustomListAdapterItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        gearsListView = findViewById<ListView>(R.id.gears_list)
        consumablesListView = findViewById<ListView>(R.id.consumables_list)

        gears = getGearsFromDevice(this, "inventory_gears")
        consumables = getConsumablesFromDevice(this, "inventory_consumables")

        gearsAdapter = CustomListAdapterItem(
            this, gears,
            onClick = {item, position ->
                showBasicDialogue(
                    this,
                    item.name, item.description,
                    "Equip", //to be changed
                    onConfirm = {}
                )
            }
        )

        consumablesAdapter = CustomListAdapterItem(
            this, consumables,
            onClick = {item, position ->
                showBasicDialogue(
                    this,
                    item.name, item.description,
                    "Equip", //to be changed
                    onConfirm = {}
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
}