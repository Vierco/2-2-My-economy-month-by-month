package com.sergio.alvarez.mieconomia

import android.widget.Toast

object ShowToast {

    fun toast (texto: String) {
        Toast.makeText(App.applicationContext(),texto,Toast.LENGTH_SHORT).show()
    }

}