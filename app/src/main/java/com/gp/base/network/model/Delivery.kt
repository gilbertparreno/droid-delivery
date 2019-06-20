package com.gp.base.network.model


import com.google.gson.annotations.SerializedName

data class Delivery(
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("location")
    val location: Location?,
    @SerializedName("id")
    val id: Int
) {
    constructor(id: Int = -1) : this(id = id, location = null)
}


data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("lat")
    val lat: Double
)