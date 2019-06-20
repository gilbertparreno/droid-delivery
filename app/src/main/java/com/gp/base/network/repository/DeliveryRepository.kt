package com.gp.base.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import com.gp.base.network.service.DeliveryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber

class DeliveryRepository(retrofit: Retrofit) : DeliveryRepositoryContract {

    private var deliveryService = retrofit.create(DeliveryService::class.java)

    private var disposable = CompositeDisposable()

    override fun getDeliveries(offset: Int, limit: Int): LiveData<ApiResponse<List<Delivery>>> {
        var mutableLiveData = MutableLiveData<ApiResponse<List<Delivery>>>()
        disposable.add(
            deliveryService
                .getDeliveries(offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    mutableLiveData.postValue(ApiResponse(data = data))
                }) { throwable ->
                    Timber.d(throwable)
                    if (throwable is HttpException && throwable.code() == 500) {
                        mutableLiveData.postValue(ApiResponse(data = listOf()))
                    } else {
                        mutableLiveData.postValue(ApiResponse(throwable = throwable))
                    }
                }
        )

        return mutableLiveData
    }
}