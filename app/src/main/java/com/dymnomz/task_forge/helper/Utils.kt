package com.dymnomz.task_forge.helper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Consumable
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.data.Item
import com.dymnomz.task_forge.data.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDate() : String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(date)
}

fun saveTasksToDevice(context: Context, key: String, gears: MutableList<Task>) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val jsonString = gson.toJson(gears)
    editor.putString(key, jsonString)
    editor.commit()
}

fun getTasksFromDevice(context: Context, key: String): MutableList<Task> {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val jsonString = sharedPreferences.getString(key, null)
    if (jsonString != null) {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return gson.fromJson(jsonString, type)
    }
    return mutableListOf() // Return an empty list if no data is found
}

fun saveGearsToDevice(context: Context, key: String, gears: MutableList<Gear>) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val jsonString = gson.toJson(gears)
    editor.putString(key, jsonString)
    editor.commit()
}

fun getGearsFromDevice(context: Context, key: String): MutableList<Gear> {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val jsonString = sharedPreferences.getString(key, null)
    if (jsonString != null) {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Gear>>() {}.type
        return gson.fromJson(jsonString, type)
    }
    return mutableListOf() // Return an empty list if no data is found
}

fun saveConsumablesToDevice(context: Context, key: String, gears: MutableList<Consumable>) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val jsonString = gson.toJson(gears)
    editor.putString(key, jsonString)
    editor.commit()
}

fun getConsumablesFromDevice(context: Context, key: String): MutableList<Consumable> {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val jsonString = sharedPreferences.getString(key, null)
    if (jsonString != null) {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Consumable>>() {}.type
        return gson.fromJson(jsonString, type)
    }
    return mutableListOf() // Return an empty list if no data is found
}

fun showPurchaseItemDialogue(
    context: Context,
    title: String,
    description: String,
    item: Item,
    onPurchase: () -> Unit
) {
    val builder = AlertDialog.Builder(context)
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.purchase_item_dialogue_layout, null)

    val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
    val descriptionTextView = dialogView.findViewById<TextView>(R.id.dialog_description)
    val itemCostTextView = dialogView.findViewById<TextView>(R.id.item_cost_tv)
    val cancelButton = dialogView.findViewById<Button>(R.id.canel_btn)
    val purchaseButton = dialogView.findViewById<Button>(R.id.purchase_btn)

    builder.setView(dialogView)
    val alertDialog = builder.create()

    titleTextView.text = title
    descriptionTextView.text = description
    itemCostTextView.text = item.cost.toString()

    purchaseButton.setOnClickListener {
        onPurchase()
        alertDialog.dismiss()
    }

    cancelButton.setOnClickListener {
        alertDialog.dismiss()
    }

    alertDialog.show()
}

fun showBasicDialogue(
    context: Context,
    title: String,
    description: String,
    confirmText: String,
    onConfirm: () -> Unit
) {
    val builder = AlertDialog.Builder(context)
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.basic_dialogue_layout, null)

    val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
    val descriptionTextView = dialogView.findViewById<TextView>(R.id.dialog_description)
    val cancelButton = dialogView.findViewById<Button>(R.id.canel_btn)
    val confirmButton = dialogView.findViewById<Button>(R.id.confirm_btn)

    confirmButton.setText(confirmText)

    builder.setView(dialogView)
    val alertDialog = builder.create()

    titleTextView.text = title
    descriptionTextView.text = description

    confirmButton.setOnClickListener {
        onConfirm()
        alertDialog.dismiss()
    }

    cancelButton.setOnClickListener {
        alertDialog.dismiss()
    }

    alertDialog.show()
}

fun getWords(text: String): List<String> {
    return text.split(" ")
}

fun convertMonthReversed(month: String) : Int {

    return when (month) {
        "January" -> 1
        "February" -> 2
        "March" -> 3
        "April" -> 4
        "May" -> 5
        "June" -> 6
        "July" -> 7
        "August" -> 8
        "September" -> 9
        "October" -> 10
        "November" -> 11
        "December" -> 12
        else -> 0
    }
}

fun convertMonth(month: String) : String {

    return when (month) {
        "1" -> "January"
        "2" -> "February"
        "3" -> "March"
        "4" -> "April"
        "5" -> "May"
        "6" -> "June"
        "7" -> "July"
        "8" -> "August"
        "9" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "Invalid Month"
    }

}