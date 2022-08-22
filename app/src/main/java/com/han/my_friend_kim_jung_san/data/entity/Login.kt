package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profilePhoto")
    val profilePhoto: String?,
    @SerializedName("userId")
    val userId: String?
)