package com.han.my_friend_kim_jung_san.data.remote.payment

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PaymentRetrofitInterface {
    @GET("/payment/{roomId}/room")
    fun searchPaymentList(
        @Path("roomId") roomId: Int
    ): Call<PaymentResponse>
}