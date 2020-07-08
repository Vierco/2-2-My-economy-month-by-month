package com.sergio.alvarez.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.sergio.alvarez.mieconomia.App.Companion.appContext
import com.sergio.alvarez.mieconomia.R

object PreferenceHelper {

    private const val FIRST_RUN = "FIRST_RUN"
    private const val OPENING_COUNTER = "OPENING_COUNTER"
    private const val USER_ID = "USER_ID"
    private const val PENDING_PAYMENTS = "PENDING_PAYMENTS"
    private const val VARIABLE_LIST_EXPENSES = "VARIABLE_LIST_EXPENSES"
    private const val ACCOUNT_ACTIVE = "ACCOUNT_ACTIVE"
    private const val IN_ACCOUNT = "IN_ACCOUNT"
    private const val FIRST_DAY = "FIRST_DAY"
    private const val LAST_MONTHLY_UPDATE = "LAST_MONTHLY_UPDATE"


    fun defaultPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    inline fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
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

    var SharedPreferences.first_run
        get() = getBoolean(FIRST_RUN, true)
        set(value) {
            editMe {
                it.putBoolean(FIRST_RUN, value)
            }
        }


    var SharedPreferences.variable_list_expense
        get() = getString(VARIABLE_LIST_EXPENSES, "")
        set(value) {
            editMe {
                it.putString(VARIABLE_LIST_EXPENSES, value)
            }
        }


    var SharedPreferences.user_id
        get() = getString(
            USER_ID, appContext.getString(
                R.string.unknow
            ))
        set(value) {
            editMe {
                it.putString(USER_ID, value)
            }
        }

    var SharedPreferences.openingCounter
        get() = getInt(OPENING_COUNTER, 0)
        set(value) {
            editMe {
                it.putInt(OPENING_COUNTER, value)
            }
        }

    var SharedPreferences.last_monthly_update
        get() = getString(LAST_MONTHLY_UPDATE, "1Month")
        set(value) {
            editMe {
                it.putString(LAST_MONTHLY_UPDATE, value)
            }
        }

    var SharedPreferences.first_day
        get() = getInt(FIRST_DAY, 1)
        set(value) {
            editMe {
                it.putInt(FIRST_DAY, value)
            }
        }

    var SharedPreferences.pendingPayments
        get() = getInt(PENDING_PAYMENTS, 0)
        set(value) {
            editMe {
                it.putInt(PENDING_PAYMENTS, value)
            }
        }

    var SharedPreferences.inAccount
        get() = getInt(IN_ACCOUNT, 0)
        set(value) {
            editMe {
                it.putInt(IN_ACCOUNT, value)
            }
        }

    var SharedPreferences.accountActive
        get() = getBoolean(ACCOUNT_ACTIVE, false)
        set(value) {
            editMe {
                it.putBoolean(ACCOUNT_ACTIVE, value)
            }
        }

/*    var SharedPreferences.clearSessionValues
        get() = { }
        set(value) {
            editMe {
//                it.remove(OPENING_COUNTER)
//                it.remove(USER_ID)
//                it.remove(PENDING_PAYMENTS)
//                it.remove(VARIABLE_LIST_EXPENSES)
//                it.remove(IN_ACCOUNT)
//                it.remove(FIRST_DAY)
                it.clear()
            }
        } */

}