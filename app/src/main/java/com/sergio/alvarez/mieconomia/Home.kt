package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.Messages.ver
import com.sergio.alvarez.mieconomia.PreferenceHelper.openingCounter
import com.sergio.alvarez.mieconomia.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class Home : AppCompatActivity() {


    private lateinit var vb: ActivityHomeBinding
    private val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())


    private fun addInt() {
        prefs.openingCounter = prefs.openingCounter + 1
    }


    private fun getDate() {

        val month = calendar.get(Calendar.MONTH) + 1
        vb.tvMounth.text = (GlobalVar.months.get(month).toString())

    }

    private fun openingChecks() {

        val opening = prefs.openingCounter
        ver("opening: $opening")

        val openingToSet = resources.getString(R.string.openingToSet).toInt()

        if (opening == openingToSet) {
            addInt()
            val intent = Intent(this, SetEmail::class.java)
            startActivity(intent)
        } else {
            addInt()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        getDate()
        openingChecks()


        fab.setOnClickListener { view ->
            // TODO ACTION
        }
    }
}
