package com.sergio.alvarez.model


class ExpenseItem(
    var concept: String? = null,
    val amount: String? = null,
    val months: List<String>? = null,
    val type: Type? = null,
    val cards_image: Int? = null,
    val payDay: Int? = null,
    val notes: String? = null
) {

    enum class Type { RECURRENT, VARIABLE }

}