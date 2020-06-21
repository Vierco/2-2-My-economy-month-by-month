package com.sergio.alvarez.mieconomia

import android.app.Application
import android.content.Context
import android.content.res.Resources

class App : Application() {


    companion object {
        lateinit var instance: Application
        lateinit var res: Resources
        lateinit var appContext: Context

        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }



    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources

        appContext = applicationContext()
    }
}