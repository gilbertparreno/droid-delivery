package com.gp.base.screen.main

import androidx.lifecycle.*
import com.gp.base.app.App
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import com.gp.base.network.repository.DeliveryRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DeliveriesViewModel @Inject constructor(private val deliveryRepository: DeliveryRepository) : ViewModel(),
    LifecycleObserver {

    val disposable = CompositeDisposable()

    val liveDataProjectList = MutableLiveData<ApiResponse<List<Delivery>>>()

    val liveFetchProgress = MutableLiveData<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        // TODO onCreate()
    }

    fun getProjectList(offset: Int, limit: Int = 20) {
        disposable.add(
            deliveryRepository
                .getDeliveries(offset, limit)
                .doOnSubscribe {
                    showLoading(true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    showLoading(false)
                    liveDataProjectList.postValue(ApiResponse(data = data))
                }) { throwable ->
                    Timber.d(throwable)
                    showLoading(false)
                    liveDataProjectList.postValue(ApiResponse(throwable = throwable))
                }
        )
    }

    fun showLoading(isLoading: Boolean) {
        liveFetchProgress.postValue(isLoading)
    }
}