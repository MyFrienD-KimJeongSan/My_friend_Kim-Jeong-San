package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

data class Pay(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("num")
    val num: Int?,
    @SerializedName("payerName")
    val payerName: String?
)