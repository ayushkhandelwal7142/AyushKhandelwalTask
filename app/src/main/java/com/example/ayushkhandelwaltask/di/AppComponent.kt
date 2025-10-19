package com.example.ayushkhandelwaltask.di

import com.example.ayushkhandelwaltask.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}


