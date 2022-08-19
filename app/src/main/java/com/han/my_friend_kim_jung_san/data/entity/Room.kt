package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

//모임방 생성
data class Room(
    @SerializedName("color")
    val color: String,
    @SerializedName("group")
    val group: List<Int>?,
    @SerializedName("name")
    val name: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("startTime")
    val startTime: String
)