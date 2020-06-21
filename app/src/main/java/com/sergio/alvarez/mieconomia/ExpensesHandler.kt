package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.EXPENSE_ID
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.generalExpensesList
import com.sergio.alvarez.mieconomia.databinding.ActivityExpensesHandlerBinding
import com.sergio.alvarez.model.ExpenseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpensesHandler : AppCompatActivity() {

    lateinit var vb: ActivityExpensesHandlerBinding
    var concept: String? = null

    // Alternative Leiv
    private lateinit var imageCard: String


    private fun paidOut2In() {
        vb.paid.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.out_to_int)
        vb.paid.startAnimation(animation)

        // ALTERNATIVE - Leiv
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

                val intent = Intent()
                intent.putExtra("removeExpense", concept)
                setResult(1, intent)
                finish()

            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })

    }

    private fun showDetails() {

        vb.expenseConcept.text = concept
        vb.expenseImage.imageCardAssignation(imageCard.toInt())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        vb = ActivityExpensesHandlerBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val conceptExtra = intent.getStringExtra(EXPENSE_ID)

        lifecycleScope.launch {
            val expenses = withContext(Dispatchers.IO) { generalExpensesList }
            val expense: ExpenseItem? = expenses.firstOrNull { it.concept == conceptExtra }

            expense?.let {
                imageCard = expense.cards_image.toString()
                concept = expense.concept

                showDetails()
            }
        }


        /* ALTERNATIVE - Leiv
        val expensesList = itemsProvider.getExpenses()
        expensesList.forEach() { expenseItem: ExpenseItem ->
         Unit

        if (expenseItem.id == intExtra) {

               image_card = expenseItem.cards_image.toString()
               concept = expenseItem.concept

            showDetails()
         }
        } */

        vb.btNotYet.setOnClickListener { finish() }


        vb.btDone.setOnClickListener { paidOut2In() }


    }
}
