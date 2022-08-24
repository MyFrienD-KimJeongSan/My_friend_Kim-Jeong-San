package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

//정산하기(정산생성)
data class Payment(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("group")
    val group: List<String>?,
    @SerializedName("payer")
    val payer: String?,
    @SerializedName("roomId")
    val roomId: Int?
)