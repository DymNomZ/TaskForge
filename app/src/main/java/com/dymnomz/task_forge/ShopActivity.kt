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