package com.example.ayushkhandelwaltask.di

import com.example.ayushkhandelwaltask.MainActivity
import com.example.feature.holdings.di.HoldingsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HoldingsModule::class, ContextModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}


