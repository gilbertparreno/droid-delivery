package com.gp.base.network.repository

import androidx.lifecycle.LiveData
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery

interface DeliveryRepositoryContract {
    fun getDeliveries(offset: Int = 0, limit: Int): LiveData<ApiResponse<List<Delivery>>>
}