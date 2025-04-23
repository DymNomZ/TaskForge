package com.dymnomz.task_forge.helper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dymnomz.task_forge.InventoryActivity
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Boss
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.data.Item
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun min(a: Int, b: Int): Int {
    return if (a <= b) a else b
}

fun checkIfExists(item: Item): Boolean{
    return InventoryActivity.gears.contains(item);
}

fun setDateOnDatePicker(context: Context, datePicker: DatePicker, dateString: String) {

    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    try {

        val date = dateFormat.parse(dateString) ?: run {
            Toast.makeText(context, "Error: Date string is null", Toast.LENGTH_LONG).show()
            return
        }

        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePicker.init(year, month, day, null)

    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error parsing date: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
fun getCurrentDate() : String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(date)
}

fun showBossViewDialogue(
    context: Context,
    boss: Boss,
    onAction: () -> Unit,
) {
    val builder = AlertDialog.Builder(context)
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.boss_view_dialogue_layout, null)

    val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
    val descriptionTextView = dialogView.findViewById<TextView>(R.id.dialog_description)
    val actionButton = dialogView.findViewById<Button>(R.id.action_btn)
    val imageView = dialogView.findViewById<ImageView>(R.id.imageview_img)
    val requiredLevel = dialogView.findViewById<TextView>(R.id.required_level_tv)

    builder.setView(dialogView)
    val alertDialog = builder.create()

    titleTextView.text = boss.name
    descriptionTextView.text = boss.desc
    requiredLevel.text = boss.required_level.toString()
    imageView.setImageResource(boss.img)
    imageView.drawable?.isFilterBitmap = false

    actionButton.setOnClickListener {
        onAction()
        alertDialog.dismiss()
    }

    alertDialog.show()
}

fun showInventoryItemDialogue(
    context: Context,
    item: Item,
    text: String,
    onAction: () -> Unit,
    onDiscard: () -> Unit
) {
    val builder = AlertDialog.Builder(context)
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.inventory_item_dialogue_layout, null)

    val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
    val descriptionTextView = dialogView.findViewById<TextView>(R.id.dialog_description)
    val discardButton = dialogView.findViewById<Button>(R.id.discard_btn)
    val actionButton = dialogView.findViewById<Button>(R.id.action_btn)
    val imageView = dialogView.findViewById<ImageView>(R.id.imageview_img)

    builder.setView(dialogView)
    val alertDialog = builder.create()

    titleTextView.text = item.name
    descriptionTextView.text = item.description
    imageView.setImageResource(item.img)
    imageView.drawable?.isFilterBitmap = false
    actionButton.setText(text)

    actionButton.setOnClickListener {
        onAction()
        alertDialog.dismiss()
    }

    discardButton.setOnClickListener {
        onDiscard()
        alertDialog.dismiss()
    }

    alertDialog.show()
}

fun showPurchaseItemDialogue(
    context: Context,
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
    val imageView = dialogView.findViewById<ImageView>(R.id.imageview_img)

    builder.setView(dialogView)
    val alertDialog = builder.create()

    titleTextView.text = item.name
    descriptionTextView.text = item.description
    itemCostTextView.text = item.cost.toString()
    imageView.setImageResource(item.img)
    imageView.drawable?.isFilterBitmap = false

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

fun checkGearType(gear: Gear) {

    var img = gear.img
    if(!gear.isEquipped) img = R.drawable.blank

    when(gear.type){
        "back_item" -> {
            EquipmentTracker.backSelected = img
        }
        "head_item" -> {
            EquipmentTracker.headSelected = img
        }
        "body_item" -> {
            EquipmentTracker.bodySelected = img
        }
        "eye_item" -> {
            EquipmentTracker.eyeSelected = img
        }
        "left_item" -> {
            EquipmentTracker.leftSelected = img
        }
        "right_item" -> {
            EquipmentTracker.rightSelected = img
        }
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