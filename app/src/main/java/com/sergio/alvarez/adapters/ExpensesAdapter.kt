package com.sergio.alvarez.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.alvarez.mieconomia.ExpenseItem
import com.sergio.alvarez.mieconomia.R
import com.sergio.alvarez.mieconomia.databinding.ExpenseCardBinding
import com.sergio.alvarez.mieconomia.inflate

class ExpensesAdapter(private val items: List<ExpenseItem>) :
    RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.expense_card)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val vb = ExpenseCardBinding.bind(view)

        fun bind(expenseItem: ExpenseItem) {

            with(vb) {

                exName.text = expenseItem.concept
                exAmount.text = expenseItem.amount

                pushpin.visibility = when (expenseItem.type) {
                    ExpenseItem.Type.RECURRENT -> View.VISIBLE
                    ExpenseItem.Type.VARIABLE -> View.GONE
                }
            }

        }
    }
}