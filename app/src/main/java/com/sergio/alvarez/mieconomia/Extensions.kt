package com.sergio.alvarez.mieconomia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sergio.alvarez.mieconomia.App.Companion.res
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.generalExpensesList
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.tag
import com.sergio.alvarez.mieconomia.PreferenceHelper.variable_list_expense
import com.sergio.alvarez.model.ExpenseItem
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}


fun ImageView.imageCardAssignation(index: Int?) {

    setImageResource(
        when (index) {

            0 -> R.drawable.card_default
            1 -> R.drawable.card_saving
            2 -> R.drawable.card_house
            3 -> R.drawable.card_health
            4 -> R.drawable.card_food
            5 -> R.drawable.card_card
            6 -> R.drawable.card_car
            7 -> R.drawable.card_gas
            8 -> R.drawable.card_electric
            9 -> R.drawable.card_internet
            10 -> R.drawable.card_mobile
            11 -> R.drawable.card_sport
            12 -> R.drawable.card_travell
            13 -> R.drawable.card_water
            14 -> R.drawable.card_school
            15 -> R.drawable.card_beer
            16 -> R.drawable.card_amazon
            17 -> R.drawable.card_family
            18 -> R.drawable.card_love
            19 -> R.drawable.card_leisure

            else -> R.drawable.card_amazon

        }
    )
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater
        .from(context)
        .inflate(layoutRes, this, false)
}

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>) {

    val intent = Intent(this, T::class.java).apply {
        putExtras(bundleOf(*pairs))
    }
    startActivity(intent)
}


// No context

fun inf(text: String) = Log.i(tag, text)
fun deb(text: String) = Log.d(tag, text)
fun ver(text: String) = Log.v(tag, text)
fun err(text: String) = Log.e(tag, text)

fun saveExpensesStatus(items: MutableList<ExpenseItem>) {
    if (items.isNotEmpty()) {
        val json = Gson().toJson(items)
        prefs.variable_list_expense = json

    }
}

fun recoverExpensesList(): MutableList<ExpenseItem> {

    val json = prefs.variable_list_expense
    if (TextUtils.isEmpty(json)) return Collections.emptyList()
    val type: Type = object : TypeToken<List<ExpenseItem?>?>() {}.type
    return Gson().fromJson(json, type)
}

fun updateGeneralExpensesList(exp: ExpenseItem) {

    if (generalExpensesList.size == 0) {
        generalExpensesList = emptyList<ExpenseItem>().toMutableList()
        generalExpensesList.add(exp)
    } else {
        generalExpensesList = recoverExpensesList()
        generalExpensesList.add(exp)
    }


}


fun getCurrencySymbol(): String? {
    val locale = Locale.getDefault()
    val currency = Currency.getInstance(locale)
    return currency.symbol

}

fun low(par: String) = par.toLowerCase(Locale.ROOT)

fun getRandomString(length: Int): String {
    val allowedChars: String = res.getString(R.string.ramdon_chars)
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

// fun roundDecimals(d: Double): String = String.format("%.2f", d)

fun getDate(): String =
    LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
        .toString()

fun getMonth(): String {
    val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
    val month = calendar.get(Calendar.MONTH) + 1
    return GlobalVar.months[month].toString()
}

fun getDay(): String {
    val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return day.toString()
}

fun getMilliseconds(): Long = System.currentTimeMillis()



