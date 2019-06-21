package com.gp.base.network.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Delivery(
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("location")
    val location: Location?,
    @SerializedName("id")
    val id: Int
) : Parcelable {
    constructor(id: Int = -1) : this(id = id, location = null)
}


@Parcelize
data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("lat")
    val lat: Double
) : Parcelable