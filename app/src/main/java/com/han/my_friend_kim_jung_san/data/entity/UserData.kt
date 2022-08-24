package com.han.my_friend_kim_jung_san.data.entity


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("accounts")
    val accounts: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profilePhoto")
    val profilePhoto: Any?
)