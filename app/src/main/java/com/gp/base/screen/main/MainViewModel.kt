package com.gp.base.screen.main

import androidx.lifecycle.*
import com.gp.base.app.App
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import com.gp.base.network.repository.DeliveryRepository
import javax.inject.Inject

class MainViewModel : ViewModel(),
    LifecycleObserver {

    @Inject
    lateinit var deliveryRepository: DeliveryRepository

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        // TODO onCreate()
    }

    fun init(app: App) {
        DaggerMainComponent.builder()
            .appComponent(app.appComponent)
            .mainModule(MainModule())
            .build()
            .inject(this)
    }

    fun getProjectList(offset: Int, limit: Int = 20): LiveData<ApiResponse<List<Delivery>>> {
        return deliveryRepository.getDeliveries(offset, limit)
    }
}
