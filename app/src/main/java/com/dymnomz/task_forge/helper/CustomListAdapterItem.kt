package com.dymnomz.task_forge.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Item
import com.dymnomz.task_forge.data.Task

class CustomListAdapterItem (
    val contex: Context,
    val list: List<Item>,
    val onClick: (Item, Int) -> Unit
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            convertView ?: LayoutInflater.from(contex).inflate(R.layout.item_view, parent, false)

        val item = list[position]

        val ItemNameTV = view.findViewById<TextView>(R.id.item_name_tv)
        val ItemCostTV = view.findViewById<TextView>(R.id.item_cost_tv)
        val ItemSprite = view.findViewById<ImageView>(R.id.imageview_img)

        ItemNameTV.setText(item.name)
        ItemCostTV.setText(item.cost.toString())
        ItemSprite.setImageResource(item.img)

        view.setOnClickListener {
            onClick(item, position)
        }
        return view

    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

}