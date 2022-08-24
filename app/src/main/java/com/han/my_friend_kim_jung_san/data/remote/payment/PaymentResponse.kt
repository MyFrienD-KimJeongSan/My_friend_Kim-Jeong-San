package com.han.my_friend_kim_jung_san.data.remote.payment


import com.google.gson.annotations.SerializedName
import com.han.my_friend_kim_jung_san.data.entity.Pay

data class PaymentResponse(
    @SerializedName("data")
    val list: List<Pay>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)