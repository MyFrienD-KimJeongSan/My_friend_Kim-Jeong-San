package com.han.my_friend_kim_jung_san.data.remote.payment

import com.google.gson.annotations.SerializedName

data class CreatePaymentResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
)