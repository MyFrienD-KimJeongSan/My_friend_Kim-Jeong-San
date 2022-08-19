package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile")
    val profile: String?,
    @SerializedName("userId")
    val userId: Int?
)