package com.han.my_friend_kim_jung_san.data.remote.room

import android.widget.Toast
import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.retrofit
import com.han.my_friend_kim_jung_san.data.entity.Room
import com.han.my_friend_kim_jung_san.data.entity.User
import com.han.my_friend_kim_jung_san.ui.meeting.CreateMeetingRoomView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RoomService {
    fun createRoom(createMeetingRoomView: CreateMeetingRoomView, room: Room){
        val roomService = retrofit.create(RoomRetrofitInterface::class.java)

        roomService.createRoom(room).enqueue(object : Callback<CreateRoomResponse>{
            override fun onResponse(
                call: Call<CreateRoomResponse>,
                response: Response<CreateRoomResponse>
            ) {
                val resp = response.body()!!
                when(resp.statusCode){
                    200 -> createMeetingRoomView.onCreateSuccess()
                    else -> createMeetingRoomView.onCreateFailure(resp.statusCode, resp.message)
                }
            }

            override fun onFailure(call: Call<CreateRoomResponse>, t: Throwable) {
            }

        })
    }
}