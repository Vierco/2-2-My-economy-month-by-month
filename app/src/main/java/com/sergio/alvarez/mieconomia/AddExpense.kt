package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.card_image_number
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.generalExpensesList
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.MonthsHandler.Companion.addAllMonths
import com.sergio.alvarez.mieconomia.MonthsHandler.Companion.listOfMonths
import com.sergio.alvarez.mieconomia.MonthsHandler.Companion.months
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.databinding.ActivityAddExpenseBinding
import com.sergio.alvarez.model.ExpenseItem
import me.rishabhkhanna.customtogglebutton.CustomToggleButton
import kotlin.properties.Delegates

class AddExpense : AppCompatActivity() {

    private lateinit var vb: ActivityAddExpenseBinding
    private lateinit var concept: String
    private lateinit var payday: String
    private lateinit var notes: String
    private lateinit var amount: String
    private var currentAmount by Delegates.notNull<Double>()

    companion object {
        var expenseForThisMonth = true
        var listHasBeenModified = false
    }

    private val userId = prefs.user_id

    fun clickOnMonth(v: View) {
        checkCheckBox()
        months(v)
    }

    private fun checkCheckBox() {
        if (vb.selectAll.isChecked) vb.selectAll.isChecked = false
    }

    private fun selectAll(value: Boolean) {

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

        with(vb.fieldInputPayDay) {
            this.doAfterTextChanged {
                val value = this.text.toString()
                if (value != "") {
                    if (value.toInt() > 31) {
                        toast(resources.getString(R.string.invalid_day))
                        this.text?.clear()
                    }
                }
            }
        }


        vb.selectAll.setOnClickListener {

            listOfMonths.clear()

            if (vb.selectAll.isChecked) {
                selectAll(true)
            } else {
                selectAll(false)
            }
        }

        vb.selectImage.setOnClickListener {
            val intent = Intent(this, ChooseImageForExpense::class.java)
            startActivity(intent)
            onPause()
        }

        vb.btSave.setOnClickListener {

            concept = vb.fieldInputConcept.text.toString()
            payday = vb.fieldInputPayDay.text.toString()
            notes = vb.fieldInputNotes.text.toString()
            amount = vb.fieldInputAmount.text.toString()


            if (concept.isNotEmpty() && payday.isNotEmpty() && amount.isNotEmpty() && listOfMonths.isNotEmpty()) {

                currentAmount = amount.toDouble()

                val userUid = FirebaseAuth.getInstance().currentUser?.uid

                val enumtype: ExpenseItem.Type =
                    if (listOfMonths.size == 12) ExpenseItem.Type.RECURRENT else ExpenseItem.Type.VARIABLE


                val currentExpense = ExpenseItem(
                    concept,
                    amount,
                    userUid,
                    listOfMonths,
                    enumtype,
                    card_image_number,
                    payday.toInt(),
                    notes
                )

                updateGeneralExpensesList(currentExpense)

                saveExpensesStatus(generalExpensesList)

                if (userId != null) {
                    val conceptToSend = low(concept.replace(" ", "_"))

                    dbAddExpense(conceptToSend, currentExpense)

                }

                expenseForThisMonth = listOfMonths.contains(getMonth())

                listOfMonths.clear()

                listHasBeenModified = true

                finish()

            } else {
                toast(resources.getString(R.string.some_empty_field))
            }

        }
    }

    override fun onResume() {
        super.onResume()

        if (card_image_number != 0) vb.selectImage.imageCardAssignation(card_image_number)

    }


}



