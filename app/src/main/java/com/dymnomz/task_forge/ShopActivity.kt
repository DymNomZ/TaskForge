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
import com.dymnomz.task_forge.helper.showPurchaseItemDialogue

class ShopActivity : Activity() {
    companion object {
        var lorem = "Dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

        val gears = mutableListOf(
            Gear("Helmets", 1, lorem, R.drawable.dymes),
            Gear("Armor", 2, lorem, R.drawable.dymes),
            Gear("Left Hand Item", 3, lorem, R.drawable.dymes),
            Gear("Right Hand Item", 4, lorem, R.drawable.dymes),
            Gear("Eyewear", 5, lorem, R.drawable.dymes),
            Gear("Back Item", 6, lorem, R.drawable.dymes),
        )

        val consumables = mutableListOf(
            Consumable("Potions", 1, lorem, R.drawable.dymes),
            Consumable("Meats", 2, lorem, R.drawable.dymes),
            Consumable("Fruits", 3, lorem, R.drawable.dymes),
            Consumable("Vegetables", 4, lorem, R.drawable.dymes),
            Consumable("Drinks", 5, lorem, R.drawable.dymes),
            Consumable("Snacks", 300, lorem, R.drawable.dymes),
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
                    this,
                    item.name, item.description, item,
                    onPurchase = {
                        if(checkCoins(item)){
                            gears.removeAt(position)
                            InventoryActivity.gears.add(item as Gear)
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
                    this,
                    item.name, item.description, item,
                    onPurchase = {
                        if(checkCoins(item)){
                            consumables.removeAt(position)
                            InventoryActivity.consumables.add(item as Consumable)
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