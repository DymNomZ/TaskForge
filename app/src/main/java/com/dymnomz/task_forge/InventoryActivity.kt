package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.data.Consumable
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.helper.CustomListAdapterInventory
import com.dymnomz.task_forge.helper.EquipmentTracker
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.checkGearType
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

        var userPrefsManager = UserPreferenceManager(this)
        var username = (application as UserData).username

        var mainLayout = findViewById<LinearLayout>(R.id.main)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
        }

        gearsListView = findViewById<ListView>(R.id.gears_list)
        consumablesListView = findViewById<ListView>(R.id.consumables_list)

        gears = userPrefsManager.getGearsFromDevice(this, username, "inventory_gears")
        consumables = userPrefsManager.getConsumablesFromDevice(this, username, "inventory_consumables")

        gearsAdapter = CustomListAdapterInventory(
            this, gears,
            onClick = {item, position ->

                var gear = gears[position]
                var msg: String

                if(gear.isEquipped) msg = "Unequip" else msg = "Equip"

                showInventoryItemDialogue(
                    this, item, msg,
                    onAction = {

                        gear.isEquipped = !gear.isEquipped
                        gears[position] = gear

                        //check what type
                        checkGearType(gear)

                        //save
                        userPrefsManager.saveGearsToDevice(
                            this, username,
                            "inventory_gears", gears
                        )
                        EquipmentTracker.saveEquipment(this, username)

                    },
                    onDiscard = {
                        gears.removeAt(position)
                        userPrefsManager.saveGearsToDevice(
                            this, username,
                            "inventory_gears", gears
                        )
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

                            var hp = (application as UserData).hp
                            userPrefsManager.updateHealth(username, hp)

                            Toast.makeText(this, "Restored player health by 10hp!", Toast.LENGTH_SHORT).show()

                            consumables.removeAt(position)
                            userPrefsManager.saveConsumablesToDevice(
                                this, username,
                                "inventory_consumables", consumables
                            )
                            onResume()
                        }
                        else{
                            Toast.makeText(this, "Player health is full!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onDiscard = {
                        consumables.removeAt(position)
                        userPrefsManager.saveConsumablesToDevice(
                            this, username,
                            "inventory_consumables", consumables
                        )
                        onResume()
                    }
                )
            }
        )

        gearsListView.adapter = gearsAdapter
        consumablesListView.adapter = consumablesAdapter

        val ToBossesButton = findViewById<Button>(R.id.to_bosses_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val ToProfileButton = findViewById<Button>(R.id.to_menu_btn)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            ToBossesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToShopButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToProfileButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
        } else {
            ToBossesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToShopButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToProfileButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
        }


        ToBossesButton.setOnClickListener {
            val intent = Intent(this, BossesActivity::class.java)
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