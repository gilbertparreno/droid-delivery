package com.gp.base.network.service

import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface DeliveryService {

    @GET("/deliveries")
    fun getDeliveries(@Query("offset") offset: Int, @Query("limit") limit: Int): Single<List<Delivery>>
}