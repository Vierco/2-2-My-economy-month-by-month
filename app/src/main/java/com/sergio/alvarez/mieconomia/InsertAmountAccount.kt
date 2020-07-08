package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sergio.alvarez.providers.GlobalVar.Companion.prefs
import com.sergio.alvarez.providers.PreferenceHelper.inAccount
import com.sergio.alvarez.mieconomia.databinding.ActivityInsertAmountAccountBinding

class InsertAmountAccount : AppCompatActivity() {

    lateinit var vb: ActivityInsertAmountAccountBinding

    private fun amountScreen(): String = vb.screen.text.toString()

    private fun getCurrentAmount(num: Any): String = amountScreen().plus(num.toString())

    private fun delete(): String = amountScreen().dropLast(1)

    private fun save() {
        if (amountScreen().isNotEmpty()) {

            prefs.inAccount = amountScreen().toInt()

            val intent = Intent()
            setResult(0, intent)
            finish()


        } else {
            toast(resources.getString(R.string.no_amount))
        }
    }


    fun clickOnNum(v: View) {

        val tag: Any = v.tag.toString()
        vb.screen.text = getCurrentAmount(tag)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        vb = ActivityInsertAmountAccountBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.delete.setOnClickListener {
            vb.screen.text = delete()
        }

        vb.save.setOnClickListener {
            save()
        }

    }
}
