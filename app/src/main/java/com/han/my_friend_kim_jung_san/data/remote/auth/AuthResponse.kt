package com.han.my_friend_kim_jung_san.data.remote.auth


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)