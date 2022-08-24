package com.han.my_friend_kim_jung_san.data.remote.register


import com.google.gson.annotations.SerializedName
import com.han.my_friend_kim_jung_san.data.entity.UserData

data class SearchUserResponse(
    @SerializedName("data")
    val userData: UserData?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)