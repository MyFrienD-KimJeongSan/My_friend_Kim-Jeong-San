package com.han.my_friend_kim_jung_san.data.remote.auth

import com.han.my_friend_kim_jung_san.data.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/user")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/user/login")
    fun login(@Body user:User): Call<AuthResponse>
}