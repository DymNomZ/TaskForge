package com.dymnomz.task_forge.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Boss

class CustomListAdapterBoss (
    val contex: Context,
    val list: List<Boss>,
    val onClick: (Boss, Int) -> Unit
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            convertView ?: LayoutInflater.from(contex).inflate(R.layout.boss_view, parent, false)

        val boss = list[position]

        val BossNameTV = view.findViewById<TextView>(R.id.boss_name_tv)
        val BossSprite = view.findViewById<ImageView>(R.id.imageview_img)
        val BossHPTV = view.findViewById<TextView>(R.id.boss_hp_tv)

        BossNameTV.setText(boss.name)
        BossSprite.setImageResource(boss.img)
        BossHPTV.setText(boss.hp.toString())
        BossSprite.drawable?.isFilterBitmap = false //prevents blurring

        view.setOnClickListener {
            onClick(boss, position)
        }
        return view

    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()
}

