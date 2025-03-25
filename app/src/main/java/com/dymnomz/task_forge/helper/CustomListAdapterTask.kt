package com.dymnomz.task_forge.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Task

class CustomListAdapterTask (
    val contex: Context,
    val listofTasks: List<Task>,
    val onClick: (Task, Int) -> Unit
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(contex).inflate(R.layout.task_layout, parent, false)

        val task =listofTasks[position]

        val imageViewPhoto = view.findViewById<ImageView>(R.id.imageview_img)
        val textViewLabel = view.findViewById<TextView>(R.id.textview_label)

        imageViewPhoto.setImageResource(task.img)
        textViewLabel.setText(task.label)

        view.setOnClickListener{
            onClick(task, position)
        }
        return view

    }

    override fun getCount(): Int = listofTasks.size

    override fun getItem(position: Int): Any = listofTasks[position]

    override fun getItemId(position: Int): Long = position.toLong()


}