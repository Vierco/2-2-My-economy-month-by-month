package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.database
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.GolbalFun.Companion.getRandomString
import com.sergio.alvarez.mieconomia.GolbalFun.Companion.low
import com.sergio.alvarez.mieconomia.Messages.toast
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivitySetIdBinding
import com.sergio.alvarez.model.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class SetId : AppCompatActivity() {

    private lateinit var vb: ActivitySetIdBinding
    private lateinit var name: String


    private fun goToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()

    }


    private fun getDate(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            .toString()

    private fun getMilliseconds(): Long = System.currentTimeMillis()

    private fun checkUserInDataBase() {

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val nameToLowerCase = low(name)

                val post: Any? = dataSnapshot.child("users").child(nameToLowerCase).value

                if (post != null) {
                    toast(resources.getString(R.string.isAlreadyInUse))
                } else {

                    prefs.user_id = name

                    val user = User(name, getDate(), getMilliseconds(), getRandomString(20))

                    database.child("users").child(nameToLowerCase).setValue(user)

                    goToHome()
                    finishAffinity()

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
        vb = ActivitySetIdBinding.inflate(layoutInflater)
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

        vb.logIn.setOnClickListener {
            toast(resources.getString(R.string.feature_to_do))
        }

    }
}
