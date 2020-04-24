package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sergio.alvarez.adapters.ExpensesAdapter
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.database
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.months
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.openingCounter
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import java.util.*

class Home : AppCompatActivity() {


    private lateinit var vb: ActivityHomeBinding
    private val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
    private val userId = prefs.user_id
    
    private fun addInt() {
        prefs.openingCounter = prefs.openingCounter + 1
    }


    private fun getDate() {

        val month = calendar.get(Calendar.MONTH) + 1
        vb.tvMonth.text = (months.get(month).toString())

        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        if (dayOfMonth == 1){
            TODO("Recolecta de datos")
        }

    }

    private fun openingChecks() {

        val opening = prefs.openingCounter

        val openingToSet = resources.getString(R.string.openingToSet).toInt()

        if (opening == openingToSet) {
            addInt()
            startActivity<SetEmail>()
        } else {
            addInt()
        }

    }

    fun showExpenses() {

        recyclerExpenses.adapter = ExpensesAdapter(getExpenses())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        getDate()
        openingChecks()
        showExpenses()

        vb.fab.setOnClickListener {
            startActivity<AddExpense>()
            onPause()
        }

        vb.fabUpdate.setOnClickListener {

        }




    }
}
