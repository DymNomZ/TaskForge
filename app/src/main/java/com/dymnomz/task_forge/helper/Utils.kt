package com.dymnomz.task_forge.helper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dymnomz.task_forge.R
import com.dymnomz.task_forge.data.Gear
import com.dymnomz.task_forge.data.Item
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun min(a: Int, b: Int): Int {
    return if (a <= b) a else b
}

fun getCurrentDate() : String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(date)
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