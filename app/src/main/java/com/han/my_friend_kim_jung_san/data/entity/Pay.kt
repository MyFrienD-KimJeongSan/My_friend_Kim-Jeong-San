package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

//정산목록 불러오기 data list
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