package com.sergio.alvarez.mieconomia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.sergio.alvarez.mieconomia.App.Companion.res
import java.util.*

fun Context.toast (message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message,length).show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
 return LayoutInflater
     .from(context)
     .inflate(layoutRes, this, false)
}

inline fun <reified T: Activity> Context.startActivity() {
    val intent = Intent (this, T::class.java)
    startActivity(intent)
}


// No context

fun inf (text: String) = Log.i(GlobalVar.tag,text)
fun deb (text: String) = Log.d(GlobalVar.tag,text)
fun ver (text: String) = Log.v(GlobalVar.tag,text)
fun err (text: String) = Log.e(GlobalVar.tag,text)


fun low (par: String) = par.toLowerCase(Locale.ROOT)

fun getRandomString(length: Int) : String {
    val allowedChars: String = res.getString(R.string.ramdon_chars)
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun roundDecimals(d: Double): String = String.format("%.2f", d)

fun getString(month: Int) = res.getString(month)

