package com.han.my_friend_kim_jung_san.data.remote.payment

import com.han.my_friend_kim_jung_san.data.entity.Payment
import retrofit2.Call
import retrofit2.http.*

interface PaymentRetrofitInterface {
    @GET("/payment/{roomId}/room")
    fun searchPaymentList(
        @Path("roomId") roomId: Int
    ): Call<PaymentResponse>


    @POST("/payment")
    fun createPayment(
        @Body payment: Payment
    ): Call<CreatePaymentResponse>
}