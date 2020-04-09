package com.sergio.alvarez.mieconomia

import java.util.*

class GolbalFun {

    companion object {

        fun low (par: String): String = par.toLowerCase(Locale.ROOT)

        fun getRandomString(length: Int) : String {
            val allowedChars: String = App.resourses.getString(R.string.ramdon_chars)
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }


    }
}