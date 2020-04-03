package com.sergio.alvarez.mieconomia

import android.app.Application
import android.content.Context
import android.content.res.Resources

class App : Application() {


    companion object {
        lateinit var instance: Application
        lateinit var resourses: Resources

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }



    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources

        val context: Context = App.applicationContext()
    }
}