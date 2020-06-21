package com.sergio.alvarez.mieconomia

import com.sergio.alvarez.mieconomia.App.Companion.appContext
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.database
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.user_id
import com.sergio.alvarez.model.ExpenseItem
import com.sergio.alvarez.model.User


val userId = prefs.user_id

fun dbAddExpense(concept: String, currentExpense: ExpenseItem) {

    database.child(appContext.resources.getString(R.string.word_user))
        .child(low(userId.toString()))
        .child(appContext.resources.getString(R.string.word_expenses))
        .child(low(concept.replace(" ", "_"))).setValue(currentExpense)

}

fun dbRemoveExpense(concept: String) {

    database.child(appContext.resources.getString(R.string.word_user))
        .child(low(userId.toString()))
        .child(appContext.getString(R.string.word_expenses)).child(low(concept))
        .removeValue()
}

fun dbAddGuestUser(userLowerCase: String, user: User) {
    database.child(appContext.resources.getString(R.string.word_user))
        .child(userLowerCase).setValue(user)
}

fun dbAddUser(userEmail: String, user: User) {
    database.child(appContext.resources.getString(R.string.word_user))
        .child(userEmail).setValue(user)
}

/* fun removeUser() {

}

fun db_addEmail(email: String) {
    database.child(appContext.resources.getString(R.string.word_user))
        .child(low(userId.toString()))
        .child(appContext.resources.getString(R.string.word_email_user))
        .setValue(email)
}
*/

