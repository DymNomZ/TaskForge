package com.dymnomz.task_forge.helper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.dymnomz.task_forge.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDate() : String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(date)
}

fun showCustomDialogue(
    context: Context,
    title: String,
    description: String,
    onConfirm: () -> Unit
) {
    val builder = AlertDialog.Builder(context)
    val inflater = LayoutInflater.from(context)
    val dialogView = inflater.inflate(R.layout.basic_dialogue_layout, null)

    val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
    val descriptionTextView = dialogView.findViewById<TextView>(R.id.dialog_description)
    val cancelButton = dialogView.findViewById<Button>(R.id.canel_btn)
    val confirmButton = dialogView.findViewById<Button>(R.id.confirm_btn)

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