package com.han.my_friend_kim_jung_san.data.remote.room


import com.google.gson.annotations.SerializedName
import com.han.my_friend_kim_jung_san.data.entity.Data

data class ScheduleResponse(
    @SerializedName("data")
    val list: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)