package com.dymnomz.task_forge.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Task

class CustomListAdapterTask (
    val contex: Context,
    val listofTasks: List<Task>,
    val onClick: (Task, Int) -> Unit,
    val completeTask: (Task, Int) -> Unit,
    val deleteTask: (Task, Int) -> Unit
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(contex).inflate(R.layout.task_layout, parent, false)

        val task =listofTasks[position]

        val TaskTitleTV = view.findViewById<TextView>(R.id.task_title_tv)
        val TaskDifficultyTV = view.findViewById<TextView>(R.id.task_difficulty_tv)
        val TaskDueTV = view.findViewById<TextView>(R.id.task_due_tv)
        val CheckBox = view.findViewById<CheckBox>(R.id.complete_task_cb)
        val DeleteBtn = view.findViewById<ImageButton>(R.id.delete_task_btn)

        TaskTitleTV.setText(task.title)
        TaskDifficultyTV.setText(task.difficulty)
        TaskDueTV.setText(task.due)
        CheckBox.isChecked = false

        CheckBox.setOnClickListener{
            completeTask(task, position)
        }

        DeleteBtn.setOnClickListener{
            deleteTask(task,position)
        }

        view.setOnClickListener{
            onClick(task, position)
        }
        return view

    }

    override fun getCount(): Int = listofTasks.size

    override fun getItem(position: Int): Any = listofTasks[position]

    override fun getItemId(position: Int): Long = position.toLong()


}