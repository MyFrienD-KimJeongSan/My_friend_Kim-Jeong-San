package com.han.my_friend_kim_jung_san.data.remote.room

import com.han.my_friend_kim_jung_san.data.entity.Room
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RoomRetrofitInterface {
    @POST("/room")
    fun createRoom(@Body room: Room): Call<CreateRoomResponse>

    @GET("/room/schedule")
    fun searchRoom(
        @Query("userId") userId: String?,
        @Query("date") date: String?
    ):Call<ScheduleResponse>
}