package com.sergio.alvarez.mieconomia

import android.app.Application
import android.content.res.Resources

class App : Application() {

    companion object {
        lateinit var instance: Application
        lateinit var resourses: Resources
    }


    // MARK: - Lifecycle

    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }
}