package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName
import com.han.my_friend_kim_jung_san.data.entity.User

//특정 일자 일정 불러오기
data class Data(
    @SerializedName("color")
    val color: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("roomId")
    val roomId: Int?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("userList")
    val userList: List<User>?
)