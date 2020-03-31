package com.sergio.alvarez.mieconomia

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PreferenceHelper {

    val PRIMERA_EJECUCION = "PRIMERA_EJECUCION"
    val VISITOR_COUNTER = "VISITOR_COUNTER"

    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    inline fun SharedPreferences.Editor.put(pair:Pair<String, Any>) {
        val key = pair.first
        val value = pair.second
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

    var SharedPreferences.firstRun
        get() = getBoolean(PRIMERA_EJECUCION, true)
        set(value) {
            editMe {
                it.putBoolean(PRIMERA_EJECUCION, value)
            }
        }

    var SharedPreferences.visitorCounter
        get() = getInt(VISITOR_COUNTER, 0)
        set(value) {
            editMe {
                it.putInt(VISITOR_COUNTER, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                /*it.remove(USER_ID)
                it.remove(USER_PASSWORD)*/
                it.clear()
            }
        }

}