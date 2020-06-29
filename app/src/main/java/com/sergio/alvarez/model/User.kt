package com.sergio.alvarez.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var name_user: String = "",
    val expenses: String? = "",
    var registration_date: String? = null,
    val milliseconds: Long?,
    var rnd_user: String? = null,
    var email_user: String? = ""
)

