package com.han.my_friend_kim_jung_san.data.remote.register

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRetrofitInterface {
    @GET("/user/{userId}/register")
    fun searchUser(
        @Path("userId") userId : String
    ): Call<SearchUserResponse>
}