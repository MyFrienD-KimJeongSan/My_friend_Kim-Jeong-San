package com.han.my_friend_kim_jung_san.data.remote.auth

import com.google.gson.annotations.SerializedName

data class Auth(@SerializedName("jwt") val jwt: String)

data class AuthResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Auth?
)