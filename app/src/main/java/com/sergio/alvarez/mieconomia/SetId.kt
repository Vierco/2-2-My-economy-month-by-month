package com.sergio.alvarez.mieconomia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.sergio.alvarez.mieconomia.ShowToast.toast
import com.sergio.alvarez.mieconomia.databinding.ActivitySetIdBinding
import java.util.*


class SetId : AppCompatActivity() {

    private lateinit var vb: ActivitySetIdBinding

    private lateinit var database: DatabaseReference

    private lateinit var name: String


    private fun checkUserInDataBase() {

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post: Any? = dataSnapshot.child("users").child(name).value

                if (post != null) {
                    toast(resources.getString(R.string.isAlreadyInUse))
                } else {
                    database.child("users").child(name).child("mail")
                        .setValue(resources.getString(R.string.db_unknown_email))
                    // TODO cambio de actividad
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

        database = FirebaseDatabase.getInstance().reference

        vb.btAccept.setOnClickListener {

            name = vb.fieldInputId.text.toString().toLowerCase(Locale.ROOT)
            val idConfirmation = vb.fieldConfirmId.text.toString().toLowerCase(Locale.ROOT)

            if (name != idConfirmation) {
                toast(resources.getString(R.string.fields_not_match))
            } else {
                checkUserInDataBase()
            }
        }

    }
}
