package com.sergio.alvarez.mieconomia

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.Messages.toast
import com.sergio.alvarez.mieconomia.databinding.ActivitySetEmailBinding
import kotlinx.android.synthetic.main.activity_set_email.*

class SetEmail : AppCompatActivity() {

    private lateinit var vb: ActivitySetEmailBinding


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

            if (email.isNotEmpty()){

                if (!isEmailValid(email)) {
                    fieldInputEmail.setError(resources.getString(R.string.invalid_email_format))
                } else {
                    // TODO Realizar envío
                }

            } else {
                toast(resources.getString(R.string.email_field_empty))
            }

        }
    }
}
