package com.example.ayushkhandelwaltask

import android.app.Application
import com.example.ayushkhandelwaltask.di.AppComponent
import com.example.ayushkhandelwaltask.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}


