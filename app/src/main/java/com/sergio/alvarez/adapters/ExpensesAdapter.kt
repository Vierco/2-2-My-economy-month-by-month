package com.sergio.alvarez.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.alvarez.mieconomia.R
import com.sergio.alvarez.mieconomia.databinding.ExpenseCardBinding
import com.sergio.alvarez.mieconomia.getCurrencySymbol
import com.sergio.alvarez.mieconomia.imageCardAssignation
import com.sergio.alvarez.mieconomia.inflate
import com.sergio.alvarez.model.ExpenseItem
import kotlin.properties.Delegates

class ExpensesAdapter(
    items: List<ExpenseItem> = emptyList(),
    private val listener: (rand: ExpenseItem, longClick: Boolean) -> Unit
) :
    RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {

    var items: List<ExpenseItem> by Delegates.observable(items) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.expense_card)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item, false) }
        holder.itemView.setOnLongClickListener {
            listener(item, true)
            true
        }
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val vb = ExpenseCardBinding.bind(view)

        fun bind(expenseItem: ExpenseItem) {

            with(vb) {

                exName.text = expenseItem.concept
                exAmount.text = (expenseItem.amount + " " + getCurrencySymbol())
                exImage.imageCardAssignation(expenseItem.cards_image)
                exPayday.text = expenseItem.payDay.toString()
                exNotes.text = expenseItem.notes

                pushpin.visibility = when (expenseItem.type) {
                    ExpenseItem.Type.RECURRENT -> View.VISIBLE
                    ExpenseItem.Type.VARIABLE -> View.GONE
                    else -> 0 //todo cnnq
                }

            }

        }
    }
}