package com.sergio.alvarez.mieconomia

import android.util.Log
import android.widget.Toast
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.tag

object Messages {

    fun toast (text: String) {
        Toast.makeText(App.applicationContext(),text,Toast.LENGTH_SHORT).show()
    }

    fun inf (text: String) = Log.i(tag,text)
    fun deb (text: String) = Log.d(tag,text)
    fun ver (text: String) = Log.v(tag,text)
    fun err (text: String) = Log.e(tag,text)

}