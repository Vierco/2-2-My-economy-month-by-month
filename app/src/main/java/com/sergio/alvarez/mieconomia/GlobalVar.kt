package com.sergio.alvarez.mieconomia

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sergio.alvarez.mieconomia.App.Companion.res

class GlobalVar {

    companion object {

        val months = mapOf(
            1 to res.getString(R.string.month_jan),
            2 to res.getString(R.string.month_feb),
            3 to res.getString(R.string.month_mar),
            4 to res.getString(R.string.month_apr),
            5 to res.getString(R.string.month_may),
            6 to res.getString(R.string.month_jun),
            7 to res.getString(R.string.month_jul),
            8 to res.getString(R.string.month_aug),
            9 to res.getString(R.string.month_sep),
            10 to res.getString(R.string.month_oct),
            11 to res.getString(R.string.month_nov),
            12 to res.getString(R.string.month_dec)
        )

        const val tag = "tag2134"

        private val CUSTOM_PREF_NAME = res.getString(R.string.custom_pref_name)

        val prefs = PreferenceHelper.customPreference(App.applicationContext(), CUSTOM_PREF_NAME)

        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    }
}