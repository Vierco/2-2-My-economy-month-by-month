package com.sergio.alvarez.mieconomia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.auth
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivityAuthBinding
import com.sergio.alvarez.model.User

class Auth : AppCompatActivity() {

    private lateinit var vb: ActivityAuthBinding
    private lateinit var email: String
    private lateinit var pass: String


    private fun registerExceptions(errorMessage: String?) {

        when (errorMessage) {
            "The email address is already in use by another account." -> toast(resources.getString(R.string.emailAlreadyInUse))
            "The email address is badly formatted." -> {
                vb.fieldInputEmail.error = resources.getString(R.string.invalid_format)
                toast(resources.getString(R.string.invalid_format))
            }
            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> toast(
                resources.getString(R.string.noInternet)
            )

        }
    }

    private fun signInExceptions(errorMessage: String?) {

        when (errorMessage) {
            "There is no user record corresponding to this identifier. The user may have been deleted." -> toast(
                resources.getString(R.string.userNotExists)
            )
            "The password is invalid or the user does not have a password." -> toast(
                resources.getString(R.string.wrongPass)
            )
            "The email address is badly formatted." -> {
                vb.fieldInputEmail.error = resources.getString(R.string.invalid_format)
                toast(resources.getString(R.string.invalid_format))
            }
            "The email address is already in use by another account" -> toast(resources.getString(R.string.invalid_password))
            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> toast(
                resources.getString(R.string.noInternet)

            )

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.guest.setOnClickListener {
            startActivity<SetGuestId>()
        }

        vb.register.setOnClickListener {

            email = vb.fieldInputEmail.text.toString()
            pass = vb.fieldPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                if (pass.length >= 8) {

                    vb.loading.visibility = View.VISIBLE

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            registerUser()
                        } else {
                            registerExceptions(it.exception?.message)
                            vb.loading.visibility = View.GONE
                        }
                    }
                } else {
                    toast(resources.getString(R.string.minimal_eight_for_pass))
                }

            } else {
                toast(resources.getString(R.string.some_empty_field), Toast.LENGTH_LONG)
            }
        }

        vb.signin.setOnClickListener {

            email = vb.fieldInputEmail.text.toString()
            pass = vb.fieldPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                if (pass.length > 7) {

                    vb.loading.visibility = View.VISIBLE

                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            signInUser()
                        } else {
                            signInExceptions(it.exception?.message)
                            vb.loading.visibility = View.GONE
                        }
                    }
                } else {
                    toast(resources.getString(R.string.minimal_eight_for_pass))
                }

            } else {
                toast(resources.getString(R.string.some_empty_field), Toast.LENGTH_LONG)
            }
        }


    }


    private fun registerUser() {
        val modifiedUser = email.replace(".", "_")

        prefs.user_id = modifiedUser
        val user = User(modifiedUser,getDate(), getMilliseconds(),getRandomString(20))
        dbAddUser(modifiedUser, user)

        startActivity<Home>()

        finishAffinity()
    }

    private fun signInUser() {
        val modifiedUser = email.replace(".", "_")

        prefs.user_id = modifiedUser

        startActivity<Home>()

        finishAffinity()
    }
}
