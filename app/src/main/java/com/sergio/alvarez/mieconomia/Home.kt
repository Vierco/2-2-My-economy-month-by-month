package com.sergio.alvarez.mieconomia

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sergio.alvarez.adapters.ExpensesAdapter
import com.sergio.alvarez.mieconomia.AddExpense.Companion.expenseForThisMonth
import com.sergio.alvarez.mieconomia.AddExpense.Companion.listHasBeenModified
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.EXPENSE_ID
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.database
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.generalExpensesList
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.pendingPayments
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.updatingPendingPayments
import com.sergio.alvarez.mieconomia.PreferenceHelper.accountActive
import com.sergio.alvarez.mieconomia.PreferenceHelper.first_day
import com.sergio.alvarez.mieconomia.PreferenceHelper.first_run
import com.sergio.alvarez.mieconomia.PreferenceHelper.inAccount
import com.sergio.alvarez.mieconomia.PreferenceHelper.last_monthly_update
import com.sergio.alvarez.mieconomia.PreferenceHelper.openingCounter
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.mieconomia.PreferenceHelper.variable_list_expense
import com.sergio.alvarez.mieconomia.databinding.ActivityHomeBinding
import com.sergio.alvarez.mieconomia.databinding.ConfirmResetBinding
import com.sergio.alvarez.mieconomia.databinding.DeleteFromDbBinding
import com.sergio.alvarez.model.ExpenseItem
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class Home : AppCompatActivity() {


    private lateinit var vb: ActivityHomeBinding
    private lateinit var conceptToRemove: String
    private val userId = prefs.user_id

    private val adapter = ExpensesAdapter { expenseItem: ExpenseItem, longClick: Boolean ->

        if (longClick) {
            deleteFromDB(expenseItem)

        } else {

            val intent = Intent(this, ExpensesHandler::class.java)
            intent.putExtras(bundleOf(EXPENSE_ID to expenseItem.concept))
            startActivityForResult(intent, 1)
        }

    }

    private fun dialogShowFilters() {

        val builder = AlertDialog.Builder(this)

        with(builder) {
            setIcon(R.drawable.ic_filter_list_black_24dp)
            setCancelable(true)
            setTitle(resources.getString(R.string.apply_filters))
            setItems(resources.getStringArray(R.array.menu_home_array)) { _, position ->

                updateExpenses(position)

            }

        }

        val alertDialog = builder.create()
        alertDialog.show()


    }

    private fun dialogResetExpenses() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 40)
        dialog.window?.setBackgroundDrawable(inset)
        dialog.setCancelable(true)

        val dg: ConfirmResetBinding = ConfirmResetBinding.inflate(layoutInflater)
        dialog.setContentView(dg.root)

        dg.dgCancel.setOnClickListener {
            dialog.dismiss()
        }

        dg.dgReset.setOnClickListener {

            dialog.dismiss()

            vb.includes.loading.visibility = View.VISIBLE
            resetGeneralExpensesList()
        }

        dialog.show()

    }

    private fun addVIsit() {
        prefs.openingCounter = prefs.openingCounter + 1
    }

    private fun getDate() {

        val currentSequence = getDay().plus(getMonth())

        if (prefs.first_day.toString() == getDay()) {

            if (currentSequence != prefs.last_monthly_update) {

                vb.includes.loading.visibility = View.VISIBLE
                resetGeneralExpensesList()
                prefs.last_monthly_update = getDay().plus(getMonth())

            } else {
                loadAndShowExpenses()
            }
        } else {
            loadAndShowExpenses()
        }

    }

    private fun openingChecks() {

        val opening = prefs.openingCounter

        // TODO("7 to 10")
        val openingToSet = resources.getString(R.string.openingToSet).toInt()

        if (opening == openingToSet) {
            addVIsit()
            // TODO (register option - guest mode)
        } else {
            addVIsit()
        }

    }

    private fun showImageForEmptyList() {

        database
            .child(App.res.getString(R.string.word_user))
            .child(prefs.user_id.toString())
            .child("expenses")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {
                        yoga.visibility = View.VISIBLE
                        emptyExpenses.visibility = View.GONE
                        recyclerExpenses.visibility = View.GONE
                    } else {
                        emptyExpenses.visibility = View.VISIBLE
                        recyclerExpenses.visibility = View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    toast(resources.getString(R.string.connection_error))
                }
            }
            )
    }

    private fun checkEmptyList() {

        with(vb.includes) {

            if (generalExpensesList.isEmpty()) {
                showImageForEmptyList()
            } else {
                emptyExpenses.visibility = View.GONE
                yoga.visibility = View.GONE
                recyclerExpenses.visibility = View.VISIBLE
                recyclerExpenses.adapter = adapter
                updateExpenses(0)


            }
        }
    }

    private fun updateExpenses(filter: Int) {

        GlobalScope.launch(Dispatchers.Main) {
            vb.includes.loading.visibility = View.VISIBLE

            adapter.items = withContext(Dispatchers.IO) { getFilteredExpenses(filter) }

            vb.includes.loading.visibility = View.GONE

        }

    }

    private fun getFilteredExpenses(filter: Int): List<ExpenseItem> {
        return generalExpensesList.let { expenseList ->
            when (filter) {
                0 -> expenseList
                1 -> expenseList.filter { it.type == ExpenseItem.Type.RECURRENT }
                2 -> expenseList.filter { it.type == ExpenseItem.Type.VARIABLE }

                else -> expenseList
            }
        }
    }

    private fun getPendingPayments() {

        pendingPayments = 0
        updatingPendingPayments = 0

        // ALTERNATIVE - Leiv Repeat ExpenseHandler.kt
        generalExpensesList.forEach { expenseItem: ExpenseItem ->
            Unit

            val conversion = expenseItem.amount!!.toInt() // TODO cnnq

            updatingPendingPayments += conversion

        }

        pendingPayments = updatingPendingPayments

        vb.includes.tvOutstanding.text = pendingPayments.toString()
        vb.includes.tvBadge.text = getCurrencySymbol()

    }

    private fun calculateAvailable(): String {
        val pending = pendingPayments
        val account = prefs.inAccount

        return (account - pending).toString()
    }

    private fun showMoneyInAccount() {

        with(vb.includes) {
            accountsMoney.text = prefs.inAccount.toString()
            accountsMoney.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.primary_dark
                )
            )
            symbol.visibility = View.VISIBLE
            symbol.text = getCurrencySymbol()
            layoutAvailable.visibility = View.VISIBLE
            amountAvailable.text = calculateAvailable()
            symbolAvailable.text = getCurrencySymbol()
        }

        prefs.accountActive = true

    }

    private fun calculateQuantities() {
        getPendingPayments()

        if (prefs.accountActive) {
            showMoneyInAccount()
        }
    }

    private fun deleteFromDB(expenseItem: ExpenseItem) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 40)
        dialog.window?.setBackgroundDrawable(inset)
        dialog.setCancelable(true)

        val dg: DeleteFromDbBinding = DeleteFromDbBinding.inflate(layoutInflater)
        dialog.setContentView(dg.root)


        dg.dgCancel.setOnClickListener {
            dialog.dismiss()
        }
        dg.dgDelete.setOnClickListener {

            val conversion = low(expenseItem.concept.toString()).replace(" ", "_")

            vb.includes.loading.visibility = View.VISIBLE
            dbRemoveExpense(conversion) //todo cnnq
            generalExpensesList = recoverExpensesList()
            generalExpensesList.removeAll { it.concept == expenseItem.concept }

            if (generalExpensesList.size == 0) {
                reloadList()
            }

            saveExpensesStatus(generalExpensesList)
            dialog.dismiss()
            calculateQuantities()
            updateExpenses(0)
        }
        dialog.show()

    }

    private fun removeExpense() {

        generalExpensesList.clear()

        generalExpensesList = recoverExpensesList()

        with(generalExpensesList) {

            removeAll { it.concept == conceptToRemove }
            if (this.size == 0) {
                reloadList()
            } else {
                saveExpensesStatus(this)

            }
        }

        calculateQuantities()
        updateExpenses(0)

    }

    private fun resetGeneralExpensesList() {

        val month = getMonth()
        generalExpensesList.clear()

        adapter.notifyDataSetChanged()

        database
            .child(App.res.getString(R.string.word_user))
            .child(prefs.user_id.toString())
            .child("expenses")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val uno: Boolean = dataSnapshot.exists()

                    if (uno) {
                        for (snapshot in dataSnapshot.children) {

                            val exp = snapshot.getValue(ExpenseItem::class.java)
                            if (exp?.months!!.contains(month)) {

                                updateGeneralExpensesList(exp)

                            }

                            saveExpensesStatus(generalExpensesList)

                        }
                        loadAndShowExpenses()
                    } else {
                        vb.includes.loading.visibility = View.GONE
                        emptyExpenses.visibility = View.VISIBLE
                        recyclerExpenses.visibility = View.GONE

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Cancelar")
                }
            })
    }

    private fun reloadList() {
        prefs.variable_list_expense = ""
        generalExpensesList.clear()
        showImageForEmptyList()
    }

    private fun loadAndShowExpenses() {

        checkEmptyList()
        getPendingPayments()

        if (prefs.accountActive) {
            showMoneyInAccount()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.tvMonth.text = getMonth()
        vb.includes.loading.visibility = View.VISIBLE

        if (prefs.first_run) {
            prefs.first_run = false

            resetGeneralExpensesList()

        } else {
            generalExpensesList = recoverExpensesList()
            openingChecks()
            getDate()
        }


        vb.fab.setOnClickListener {
            val intent = Intent(this, AddExpense::class.java)
            startActivityForResult(intent, 3)

        }

        vb.update.setOnClickListener {

            dialogResetExpenses()
        }

        vb.filter.setOnClickListener {
            dialogShowFilters()
        }

        vb.settings.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivityForResult(intent, 2)

        }

        vb.includes.accountsMoney.setOnClickListener {

            val intent = Intent(this, InsertAmountAccount::class.java)
            startActivityForResult(intent, 0)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            0 -> {  // Insert money account
                showMoneyInAccount()
            }

            1 -> {  // Delete item from local expenses list
                vb.includes.loading.visibility = View.VISIBLE
                conceptToRemove = data?.getStringExtra("removeExpense") ?: "0"
                removeExpense()
            }

            2 -> { // Setting activity
                if (!prefs.accountActive) {
                    with(vb.includes) {
                        layoutAvailable.visibility = View.GONE
                        symbol.visibility = View.GONE
                        accountsMoney.text = (resources.getString(R.string._not_set))
                        accountsMoney.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.orange
                            )
                        )
                    }
                }
            }

            3 -> { // add a expense

                if (listHasBeenModified) {

                    if (expenseForThisMonth) {

                        when (generalExpensesList.size) {
                            0 -> reloadList()
                            1 -> checkEmptyList()
                            else -> {
                                updateExpenses(0)
                            }
                        }
                    } else {
                        toast(
                            resources.getString(R.string.setExpanseOkNotThisMonth),
                            Toast.LENGTH_LONG
                        )
                    }

                    listHasBeenModified = false
                }
            }

        }

    }


}
