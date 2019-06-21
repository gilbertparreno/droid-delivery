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
import javax.inject.Inject

class DeliveryRepository @Inject constructor(private val deliveryService: DeliveryService) : DeliveryRepositoryContract {

    private var disposable = CompositeDisposable()

//    override fun getDeliveries(offset: Int, limit: Int): LiveData<ApiResponse<List<Delivery>>> {
//        var mutableLiveData = MutableLiveData<ApiResponse<List<Delivery>>>()

//
//        return mutableLiveData
//    }

    override fun getDeliveries(offset: Int, limit: Int) = deliveryService.getDeliveries(offset, limit)
}