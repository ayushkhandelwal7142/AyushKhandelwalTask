package com.example.ayushkhandelwaltask.di

import com.example.feature.holdings.di.HoldingsModule
import com.example.ayushkhandelwaltask.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HoldingsModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}


