package com.gp.base.screen.main

import com.gp.base.di.AppComponent
import com.gp.base.di.FragmentScope
import com.gp.base.screen.deliveries.FragmentDeliveryDetails
import com.gp.base.viewModel.ViewModelFactoryModule
import com.gp.base.viewModel.ViewModelModule
import dagger.Component

@FragmentScope
@Component(modules = [DeliveryDetailsModule::class, ViewModelFactoryModule::class, ViewModelModule::class], dependencies = [AppComponent::class])
interface DeliveryDetailsComponent {
    fun inject(fragmentDeliveryDetails: FragmentDeliveryDetails)
}