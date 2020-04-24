package com.sergio.alvarez.mieconomia

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.database
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivitySetEmailBinding
import kotlinx.android.synthetic.main.activity_set_email.*

class SetEmail : AppCompatActivity() {

    private lateinit var vb: ActivitySetEmailBinding

    private val userId = prefs.user_id


    private fun isEmailValid(email: CharSequence): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySetEmailBinding.inflate(layoutInflater)
        setContentView(vb.root)


        noThanks.setOnClickListener {
            finish()
        }

        send.setOnClickListener {

            val email = fieldInputEmail.text.toString()

            if (email.isNotEmpty()) {

                if (!isEmailValid(email)) {
                    fieldInputEmail.error = resources.getString(R.string.invalid_email_format)
                } else {
                    if (userId != null) {
                        database.child(resources.getString(R.string.word_user))
                            .child(low(userId)).child(resources.getString(R.string.word_email_user))
                            .setValue(email)
                    }

                    toast(resources.getString(R.string.setEmailOk))
                    finish()
                }

            } else {
                toast(resources.getString(R.string.empty_email_field))
            }

        }
    }
}
