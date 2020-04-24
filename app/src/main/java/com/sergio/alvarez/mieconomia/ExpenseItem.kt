package com.sergio.alvarez.mieconomia

class ExpenseItem (val concept: String, val amount: String, val type: Type) {

    enum class Type {RECURRENT, VARIABLE}
}