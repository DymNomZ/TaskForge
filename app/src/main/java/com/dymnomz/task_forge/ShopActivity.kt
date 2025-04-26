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
import com.dymnomz.task_forge.data.Item
import com.dymnomz.task_forge.helper.CustomListAdapterItem
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.checkIfExists
import com.dymnomz.task_forge.helper.showPurchaseItemDialogue

class ShopActivity : Activity() {
    companion object {

        val gears = mutableListOf(
            Gear("Helmet", 1, "Protects your noggin from unexpected dragon droppings.", R.drawable.helmet, "head_item", false),
            Gear("Armor", 2, "Shrug off sword swipes like a boss in this shiny getup.", R.drawable.armor, "body_item", false),
            Gear("Left Sword", 3, "For slicing foes with finesse (left-handed style!).", R.drawable.left_sword, "left_item", false),
            Gear("Right Sword", 4, "The perfect partner for your left sword, ready to strike!", R.drawable.right_sword, "right_item", false),
            Gear("Glasses", 5, "See the world with +5 charisma (and maybe read tiny text).", R.drawable.glasses, "eye_item", false),
            Gear("Wings", 6, "Soar through the skies, or at least look really cool trying.", R.drawable.wings, "back_item", false),
        )

        val consumables = mutableListOf(
            Consumable("Potion", 1, "Chug this down for a quick health boost (side effects may include glowing).", R.drawable.potion, "potion"),
            Consumable("Steak", 2, "A hearty meal that'll make you strong like a bear... almost.", R.drawable.steak, "meat"),
            Consumable("Mango", 3, "This sweet treat gives you a burst of sunshine energy!", R.drawable.mango, "fruit"),
            Consumable("Carrot", 4, "Crunch on this for super-vision and bunny-like hops.", R.drawable.carrot, "vegetable"),
            Consumable("Drink", 5, "Quench your thirst and maybe gain some liquid courage.", R.drawable.drink, "drink"),
            Consumable("Cake", 300, "Indulge in this sugary delight for a massive happiness overload!", R.drawable.cake, "snack"),
        )
    }

    lateinit var gearsListView: ListView
    lateinit var consumablesListView: ListView
    lateinit var gearsAdapter: CustomListAdapterItem
    lateinit var consumablesAdapter: CustomListAdapterItem

    fun checkCoins(item: Item) : Boolean{

        if((application as UserData).makePurchase(item.cost)){

            var userPrefsManager = UserPreferenceManager(this)
            var username = (application as UserData).username
            var coins = (application as UserData).coins
            userPrefsManager.updateCoins(username, coins)

            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

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

        gearsAdapter = CustomListAdapterItem(
            this, gears,
            onClick = {item, position ->
                showPurchaseItemDialogue(
                    this, item,
                    onPurchase = {
                        if(checkCoins(item)){
//                            gears.removeAt(position)

                            //temporary 'til scalable (?)
                            if(!checkIfExists(item)){
                                InventoryActivity.gears.add(item as Gear)

//                            userPrefsManager.saveGearsToDevice(
//                                this, username,
//                                "shop_gears", gears
//                            )
                                userPrefsManager.saveGearsToDevice(
                                    this, username,
                                    "inventory_gears", InventoryActivity.gears
                                )

                                onResume()
                            }
                            else{
                                Toast.makeText(this, "Already has item!", Toast.LENGTH_SHORT).show()
                            }

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

//                            userPrefsManager.saveConsumablesToDevice(
//                                this, username,
//                                "shop_consumables", consumables
//                            )
                            userPrefsManager.saveConsumablesToDevice(
                                this, username,
                                "inventory_consumables", InventoryActivity.consumables
                            )

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

        val ToBossesButton = findViewById<Button>(R.id.to_bosses_btn)
        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToMenuButton = findViewById<Button>(R.id.to_menu_btn)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            ToBossesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToMenuButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToInventoryButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
        } else {
            ToBossesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToMenuButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToInventoryButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
        }

        ToBossesButton.setOnClickListener {
            val intent = Intent(this, BossesActivity::class.java)
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