package com.sergio.alvarez.mieconomia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.database
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivitySetGuestIdBinding
import com.sergio.alvarez.model.User


class SetGuestId : AppCompatActivity() {

    private lateinit var vb: ActivitySetGuestIdBinding
    private lateinit var name: String


    private fun goToHome() {
        startActivity<Home>()
        finishAffinity()

    }

    private fun checkUserInDataBase() {

        val nameToLowerCase = low(name.replace(".","_"))

        database
            .child(resources.getString(R.string.word_user))
            .child(nameToLowerCase)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val exist = dataSnapshot.exists()

                if (exist) {
                    toast(resources.getString(R.string.isAlreadyInUse))
                } else {
                    prefs.user_id = name

                    val user = User(name,getDate(), getMilliseconds(), getRandomString(20))

                    dbAddGuestUser(nameToLowerCase, user)

                    goToHome()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                toast(resources.getString(R.string.connection_error))
            }
        }
        )

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySetGuestIdBinding.inflate(layoutInflater)
        setContentView(vb.root)


        vb.btAccept.setOnClickListener {

            name = vb.fieldInputId.text.toString()
            val idConfirmation = vb.fieldConfirmId.text.toString()

            if (name.isEmpty() || idConfirmation.isEmpty()) {
                toast(resources.getString(R.string.some_empty_field))

            } else {

                if (name.equals(idConfirmation, ignoreCase = true)) {
                    checkUserInDataBase()
                } else {
                    toast(resources.getString(R.string.fields_not_match))
                }

            }
        }


    }
}
