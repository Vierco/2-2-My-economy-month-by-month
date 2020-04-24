package com.sergio.alvarez.mieconomia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.MonthsHandler.Companion.addAllMonths
import com.sergio.alvarez.mieconomia.MonthsHandler.Companion.listOfMonths
import com.sergio.alvarez.mieconomia.MonthsHandler.Companion.months
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivityAddExpenseBinding
import com.sergio.alvarez.model.Expenses
import me.rishabhkhanna.customtogglebutton.CustomToggleButton
import kotlin.properties.Delegates

class AddExpense : AppCompatActivity() {

    private lateinit var vb: ActivityAddExpenseBinding
    private lateinit var concept: String
    private lateinit var payday: String
    private lateinit var notes: String
    private lateinit var amount: String
    private var currentAmount by Delegates.notNull<Double>()

    private val userId = prefs.user_id

    fun clickOnMonth(v: View) {
        checkCheckBox()
        months(v)
    }

    private fun checkCheckBox() { if (vb.selectAll.isChecked) vb.selectAll.isChecked = false
    inf("click")}

    private fun selectAll(value: Boolean) {

        inf("fun selectAll con boolean $value")

        if (value) addAllMonths()

        vb.includes.flexboxlayout.forEach { togglebutton ->
            (togglebutton as CustomToggleButton).isChecked = value
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.scrollingBackground.start()

        vb.selectAll.setOnClickListener {

            listOfMonths.clear()

            if (vb.selectAll.isChecked) {
                ver("ON")
                selectAll(true)
            } else {
                selectAll(false)
                err("OFF")
            }
        }

        vb.btSave.setOnClickListener {

            inf(listOfMonths.toString())

            concept = vb.fieldInputConcept.text.toString()
            payday = vb.fieldInputPayDay.text.toString()
            notes = vb.fieldInputNotes.text.toString()
            amount = vb.fieldInputAmount.text.toString()
            currentAmount = amount.toDouble()

            if(concept.isNotEmpty() && payday.isNotEmpty()){

                val current_expense = Expenses(concept, roundDecimals(currentAmount), listOfMonths, payday.toInt(), notes)

                if (userId != null) {
                    GlobalVar.database.child(resources.getString(R.string.word_user)).child(low(userId)).child(resources.getString(R.string.word_expenses)).child(low(concept.replace(" ","_"))).setValue(current_expense)
                }

                toast(resources.getString(R.string.setExpanseOk))

                listOfMonths.clear()

                finish()

            } else {
                toast(resources.getString(R.string.some_empty_field))
            }

        }
    }
}



