package com.han.my_friend_kim_jung_san.data.remote.room


import com.google.gson.annotations.SerializedName

data class CreateRoomResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)