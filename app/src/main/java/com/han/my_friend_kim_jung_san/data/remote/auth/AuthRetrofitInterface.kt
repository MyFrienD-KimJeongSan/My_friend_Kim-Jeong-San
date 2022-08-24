package com.han.my_friend_kim_jung_san.data.remote.auth

import com.han.my_friend_kim_jung_san.data.entity.Login
import com.han.my_friend_kim_jung_san.data.entity.Accounts
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthRetrofitInterface {

    @POST("/user/login")
    fun login(@Body login: Login): Call<AuthResponse>

    @POST("/user/{userId}/register")
    fun signUp(
        @Path("userId") userId: String,
        @Body accounts: Accounts
    ): Call<AuthResponse>
}