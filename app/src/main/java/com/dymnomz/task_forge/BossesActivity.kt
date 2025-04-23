package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.data.Boss
import com.dymnomz.task_forge.helper.CustomListAdapterBoss
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.showBasicDialogue
import com.dymnomz.task_forge.helper.showBossViewDialogue

class BossesActivity : Activity() {

    companion object{
        var lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."
        var bosses = mutableListOf(
            Boss("Kaido", lorem, 100, 10, 1, R.drawable.kaido),
            Boss("Kaido", lorem, 200, 10, 5, R.drawable.kaido),
            Boss("Kaido", lorem, 300, 10, 10, R.drawable.kaido),
            Boss("Kaido", lorem, 400, 10, 15, R.drawable.kaido),
            Boss("Kaido", lorem, 500, 10, 20, R.drawable.kaido),
            Boss("Kaido", lorem, 600, 10, 25, R.drawable.kaido),
            Boss("Kaido", lorem, 700, 10, 30, R.drawable.kaido)
        )

        var blankBoss = Boss("", "", 0, 0, 0, R.drawable.blank_large)

        var selectedBoss = blankBoss
    }

    fun checkLevel(boss: Boss): Boolean{
        var level = (application as UserData).level

        return level >= boss.required_level
    }

    lateinit var bossImageView: ImageView
    lateinit var bossNameTV: TextView
    lateinit var bossDescTV: TextView
    lateinit var bossHPTV: TextView
    lateinit var ForfeitBtn: Button

    fun setBossDetails(){

        if(selectedBoss.name.isNotEmpty()) bossNameTV.setText(selectedBoss.name)
        else bossNameTV.setText("No Boss Selected")

        bossDescTV.setText(selectedBoss.desc)
        bossHPTV.setText(selectedBoss.hp.toString())
        bossImageView.setImageResource(selectedBoss.img)
        bossImageView.drawable?.isFilterBitmap = false //prevents blurring

        if(selectedBoss.name.isNotEmpty()) ForfeitBtn.setText("FORFEIT")
        else ForfeitBtn.setText("")
    }

    lateinit var bossesListView: ListView
    lateinit var bossesAdapter: CustomListAdapterBoss
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bosses)

        var userPrefsManager = UserPreferenceManager(this)
        var username = (application as UserData).username

        bossImageView = findViewById<ImageView>(R.id.boss_img)
        bossNameTV = findViewById<TextView>(R.id.boss_name_tv)
        bossDescTV = findViewById<TextView>(R.id.boss_desc_tv)
        bossHPTV = findViewById<TextView>(R.id.boss_hp_tv)
        ForfeitBtn = findViewById<Button>(R.id.forfeit_btn)

        setBossDetails()

        bossesListView = findViewById<ListView>(R.id.bosses_list)

        //check if boss is still alive
        if(selectedBoss.checkIfDead()){
            selectedBoss = blankBoss

            //set resources
            setBossDetails()

            Toast.makeText(this, "Boss defeated!", Toast.LENGTH_SHORT).show()
            //award player logic
            (application as UserData).award(50, 100)
        }

        bossesAdapter = CustomListAdapterBoss(
            this, bosses,
            onClick = { boss, position ->
                showBossViewDialogue(
                    this, boss,
                    onAction = {
                        //check if currently in battle
                        if(selectedBoss.name.isNotEmpty()){
                            Toast.makeText(this, "Currently in battle with ${selectedBoss.name}!", Toast.LENGTH_SHORT).show()
                            return@showBossViewDialogue
                        }
                        if(checkLevel(boss)){
                            //set resources
                            bossImageView.setImageResource(boss.img)
                            bossNameTV.setText(boss.name)
                            bossDescTV.setText(boss.desc)
                            bossHPTV.setText(boss.hp.toString())

                            ForfeitBtn.setText("FORFEIT")

                            selectedBoss = boss

                            //remove from list
                            bosses.removeAt(position);

                            //refresh
                            onResume()
                        }
                        else{
                            Toast.makeText(this, "You do not meet the required level!", Toast.LENGTH_SHORT).show()
                            return@showBossViewDialogue
                        }
                    }
                )
            }
        )

        bossesListView.adapter = bossesAdapter

        ForfeitBtn.setOnClickListener {
            if(selectedBoss.name.isNotEmpty()){
                showBasicDialogue(
                    this, "Surrender?",
                    "You will lose xp and coins. Are you sure?",
                    "Accept Defeat",
                    onConfirm = {
                        //demote player
                        (application as UserData).forfeit()

                        //replace selectedBoss
                        selectedBoss.resetValues()
                        bosses.add(selectedBoss)

                        //sort by required level
                        bosses.sortBy { it.required_level }

                        selectedBoss = blankBoss

                        finish()
                        val intent = Intent(this, BossesActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }

        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val ToProfileButton = findViewById<Button>(R.id.to_menu_btn)

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
        bossesAdapter.notifyDataSetChanged()
    }
}