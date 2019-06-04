package com.gp.base.screen.main

import com.gp.base.di.ActivityScope
import com.gp.base.di.AppComponent
import dagger.Component

@ActivityScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun inject(mainViewModel: MainViewModel)
}