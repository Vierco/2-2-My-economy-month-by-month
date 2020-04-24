package com.sergio.alvarez.mieconomia

import com.sergio.alvarez.mieconomia.ExpenseItem.*

fun getExpenses() : List<ExpenseItem> = listOf(

ExpenseItem("Hipoteca", "386", Type.RECURRENT),
ExpenseItem("Seguro coche", "90", Type.RECURRENT),
ExpenseItem("Iberdrola", "75", Type.RECURRENT),
ExpenseItem("Yoigo", "120", Type.VARIABLE),
ExpenseItem("Supermercado", "230", Type.RECURRENT),
ExpenseItem("Gasolina", "110", Type.VARIABLE),
ExpenseItem("Préstamo", "285", Type.RECURRENT),
ExpenseItem("Vacaciones", "100", Type.VARIABLE)

)
