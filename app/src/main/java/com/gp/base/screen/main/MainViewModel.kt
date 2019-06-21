package com.gp.base.screen.main

import androidx.lifecycle.*
import com.gp.base.app.App
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import com.gp.base.network.repository.DeliveryRepository
import javax.inject.Inject

class MainViewModel : ViewModel(),
    LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        // TODO onCreate()
    }
}
