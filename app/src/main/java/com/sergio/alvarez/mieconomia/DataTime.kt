package com.sergio.alvarez.mieconomia

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sergio.alvarez.mieconomia.App.Companion.res
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id

class DataTime  {

    private val userId = prefs.user_id
    lateinit var deLaDB: Any


    // Métodos a utilizar:
    private fun test1 (){

        var one = deLaDB.toString().replace("[","")
        one = one.replace("]","")

        ver(one)

        var result: List<String> =one.split(",").map { it.trim() }

        inf("lista: ${result}")

        inf("extraido: ${result.sorted()}")

        if(result.contains("aug")){
            deb("Tiene el mes de agosto")
        } else {
            deb("No tiene agosto")
        }

    }




// Recolección de la lista de meses del gasto
    private fun addUserChangeListener() {
        GlobalVar.database.addValueEventListener(object : ValueEventListener { // addListenerForSingleValueEvent
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                deLaDB = dataSnapshot.child(res.getString(R.string.word_user)) // "users"
                    .child(userId.toString())
                    .child(res.getString(R.string.word_expenses))
                    .child("hipoteca") // Nombre del gasto
                    .child(res.getString(R.string.word_months)).value!!

                // Check for null
                if (deLaDB == null) {
                    return
                }

                inf(deLaDB.toString())

                test1()

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

  
}
