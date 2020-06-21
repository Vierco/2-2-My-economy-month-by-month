package com.sergio.alvarez.mieconomia

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.auth
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.accountActive
import com.sergio.alvarez.mieconomia.PreferenceHelper.defaultPreference
import com.sergio.alvarez.mieconomia.PreferenceHelper.first_day
import com.sergio.alvarez.mieconomia.PreferenceHelper.inAccount
import com.sergio.alvarez.mieconomia.PreferenceHelper.pendingPayments
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.PreferenceHelper.variable_list_expense
import com.sergio.alvarez.mieconomia.databinding.ActivitySettingsBinding
import com.sergio.alvarez.mieconomia.databinding.CloseSessionBinding
import it.sephiroth.android.library.numberpicker.setListener

private lateinit var vb: ActivitySettingsBinding
private lateinit var firstDay: String

class Settings : AppCompatActivity() {


    private fun closeSession() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 40)
        dialog.window?.setBackgroundDrawable(inset)
        dialog.setCancelable(true)

        val dg: CloseSessionBinding = CloseSessionBinding.inflate(layoutInflater)
        dialog.setContentView(dg.root)

        dg.dgCancel.setOnClickListener {
            dialog.dismiss()
        }

        dg.dgClose.setOnClickListener {
            auth.signOut()

            val defaultPrefs = defaultPreference(this)

            with(prefs) {
                user_id = defaultPrefs.user_id
                first_day = defaultPrefs.first_day
                first_day = defaultPrefs.first_day
                variable_list_expense = defaultPrefs.variable_list_expense
                pendingPayments = defaultPrefs.pendingPayments
                inAccount = defaultPrefs.inAccount
                accountActive = false
            }

            startActivity<Auth>()
            finishAffinity()

        }
        dialog.show()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(vb.root)

        firstDay = prefs.first_day.toString()

        vb.switchAccount.isChecked = prefs.accountActive

        vb.dayPicker.setListener {
            onProgressChanged { _, progress, _ ->
                firstDay = progress.toString()
            }

        }

        vb.btCancel.setOnClickListener {
            onBackPressed()
        }

        vb.btSave.setOnClickListener {

            prefs.accountActive = vb.switchAccount.isChecked
            prefs.first_day = firstDay.toInt()

            val intent = Intent()
            setResult(2, intent)
            finish()
        }

        vb.close.setOnClickListener {
            closeSession()
        }

        vb.about.setOnClickListener {
            startActivity<About>()
        }


    }

}