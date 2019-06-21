package com.gp.base.screen.main

import com.gp.base.di.FragmentScope
import com.gp.base.network.model.Delivery
import com.gp.base.network.repository.DeliveryRepository
import com.gp.base.network.service.DeliveryService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class DeliveriesModule{

    @Provides
    @FragmentScope
    fun providesProjectRepository(retrofit: Retrofit): DeliveryRepository {
        return DeliveryRepository(retrofit.create(DeliveryService::class.java))
    }
}