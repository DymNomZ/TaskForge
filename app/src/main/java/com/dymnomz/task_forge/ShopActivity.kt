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
import com.dymnomz.task_forge.data.Item
import com.dymnomz.task_forge.helper.CustomListAdapterItem
import com.dymnomz.task_forge.helper.saveConsumablesToDevice
import com.dymnomz.task_forge.helper.saveGearsToDevice
import com.dymnomz.task_forge.helper.showPurchaseItemDialogue

class ShopActivity : Activity() {
    companion object {
        var lorem = "Dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

        val gears = mutableListOf(
            Gear("Helmet", 1, lorem, R.drawable.helmet),
            Gear("Armor", 2, lorem, R.drawable.armor),
            Gear("Left Sword", 3, lorem, R.drawable.left_sword),
            Gear("Right Sword", 4, lorem, R.drawable.right_sword),
            Gear("Glasses", 5, lorem, R.drawable.glasses),
            Gear("Wings", 6, lorem, R.drawable.wings),
        )

        val consumables = mutableListOf(
            Consumable("Potion", 1, lorem, R.drawable.potion),
            Consumable("Steak", 2, lorem, R.drawable.steak),
            Consumable("Mango", 3, lorem, R.drawable.mango),
            Consumable("Carrot", 4, lorem, R.drawable.carrot),
            Consumable("Drink", 5, lorem, R.drawable.drink),
            Consumable("Cake", 300, lorem, R.drawable.cake),
        )
    }

    lateinit var gearsListView: ListView
    lateinit var consumablesListView: ListView
    lateinit var gearsAdapter: CustomListAdapterItem
    lateinit var consumablesAdapter: CustomListAdapterItem

    fun checkCoins(item: Item) : Boolean{

        var coins = (application as UserData).coins

        if(item.cost <= coins){

            (application as UserData).coins -= item.cost
            coins = (application as UserData).coins

            var sp = getSharedPreferences("UserData", Context.MODE_PRIVATE)
            var editor = sp.edit()
            editor.putInt("coins", coins)
            editor.commit()

            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        gearsListView = findViewById<ListView>(R.id.gears_list)
        consumablesListView = findViewById<ListView>(R.id.consumables_list)

        gearsAdapter = CustomListAdapterItem(
            this, gears,
            onClick = {item, position ->
                showPurchaseItemDialogue(
                    this, item,
                    onPurchase = {
                        if(checkCoins(item)){
//                            gears.removeAt(position)
                            InventoryActivity.gears.add(item as Gear)
                            saveGearsToDevice(this, "shop_gears", gears)
                            saveGearsToDevice(this, "inventory_gears", InventoryActivity.gears)
                            onResume()
                        }
                        else{
                            Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        )

        consumablesAdapter = CustomListAdapterItem(
            this, consumables,
            onClick = {item, position ->
                showPurchaseItemDialogue(
                    this, item,
                    onPurchase = {
                        if(checkCoins(item)){
                            InventoryActivity.consumables.add(item as Consumable)
                            saveConsumablesToDevice(this, "shop_consumables", consumables)
                            saveConsumablesToDevice(this, "inventory_consumables", InventoryActivity.consumables)
                            onResume()
                        }
                        else{
                            Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
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