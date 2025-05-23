package com.dymnomz.task_forge

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.app.UserData
import com.dymnomz.task_forge.data.Boss
import com.dymnomz.task_forge.data.Descriptions
import com.dymnomz.task_forge.helper.CustomListAdapterBoss
import com.dymnomz.task_forge.helper.UserPreferenceManager
import com.dymnomz.task_forge.helper.showBasicDialogue
import com.dymnomz.task_forge.helper.showBossViewDialogue

class BossesActivity : Activity() {

    companion object{

        var bosses: MutableList<Boss> = mutableListOf()

        var initialBosses = mutableListOf(
            Boss("Blazefin", Descriptions.BLAZEFIN, 100, 10, 1, R.drawable.blazefin),
            Boss("Sir Cutthroat", Descriptions.SIR_CUTTHROAT, 200, 10, 5, R.drawable.sir_cutthroat),
            Boss("Head Crab", Descriptions.HEAD_CRAB, 300, 10, 10, R.drawable.head_crab),
            Boss("The Gaze", Descriptions.THE_GAZE, 400, 10, 15, R.drawable.the_gaze),
            Boss("Kaido", Descriptions.KAIDO, 500, 10, 20, R.drawable.kaido),
            Boss("Emrakul", Descriptions.EMRAKUL, 600, 10, 25, R.drawable.emrakul),
            Boss("Nika", Descriptions.NIKA, 700, 10, 30, R.drawable.nika)
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

        var mainLayout = findViewById<LinearLayout>(R.id.main)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            mainLayout.setBackgroundResource(R.drawable.background_dark)
        } else {
            mainLayout.setBackgroundResource(R.drawable.background)
        }

        //get boss list from device
        bosses = userPrefsManager.getBossesFromDevice(this, username)

        //get boss from device
        selectedBoss = userPrefsManager.getSelectedBossFromDevice(this, username)

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

            //save to device
            userPrefsManager.saveSelectedBossToDevice(this, username, selectedBoss)

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

                            userPrefsManager.saveSelectedBossToDevice(this, username, selectedBoss)

                            //remove from list
                            bosses.removeAt(position);

                            //save list to device
                            userPrefsManager.saveBossesToDevice(this, username, bosses)

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

                        userPrefsManager.saveSelectedBossToDevice(this, username, selectedBoss)
                        userPrefsManager.saveBossesToDevice(this, username, bosses)

                        recreate()
                    }
                )
            }
        }

        val ToInventoryButton = findViewById<Button>(R.id.to_inventory_btn)
        val ToTasksButton = findViewById<Button>(R.id.to_tasks_btn)
        val ToShopButton = findViewById<Button>(R.id.to_shop_btn)
        val ToProfileButton = findViewById<Button>(R.id.to_menu_btn)

        if (userPrefsManager.isDarkMode("dark_mode_pref")) {
            ToInventoryButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToShopButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
            ToProfileButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange_1))
        } else {
            ToInventoryButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToTasksButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToShopButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
            ToProfileButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red_1))
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