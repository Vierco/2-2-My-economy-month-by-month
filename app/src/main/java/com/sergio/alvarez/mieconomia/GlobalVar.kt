package com.sergio.alvarez.mieconomia

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GlobalVar {

    companion object {

        val months = mapOf(
            1 to App.resourses.getString(R.string.mouth_jan),
            2 to App.resourses.getString(R.string.mouth_feb),
            3 to App.resourses.getString(R.string.mouth_mar),
            4 to App.resourses.getString(R.string.mouth_apr),
            5 to App.resourses.getString(R.string.mouth_may),
            6 to App.resourses.getString(R.string.mouth_jun),
            7 to App.resourses.getString(R.string.mouth_jul),
            8 to App.resourses.getString(R.string.mouth_aug),
            9 to App.resourses.getString(R.string.mouth_sep),
            10 to App.resourses.getString(R.string.mouth_oct),
            11 to App.resourses.getString(R.string.mouth_nov),
            12 to App.resourses.getString(R.string.mouth_dec)
        )

        const val tag = "tag2134"

        private val CUSTOM_PREF_NAME = App.resourses.getString(R.string.custom_pref_name)

        val prefs = PreferenceHelper.customPreference(App.applicationContext(), CUSTOM_PREF_NAME)

        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    }
}