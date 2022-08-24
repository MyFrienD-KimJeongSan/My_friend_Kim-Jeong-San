package com.han.my_friend_kim_jung_san.data.remote.payment

import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.retrofit
import com.han.my_friend_kim_jung_san.ui.calculation.PaymentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PaymentService {
    fun searchPaymentList(paymentView: PaymentView, roomId: Int){
        val paymentService = retrofit.create(PaymentRetrofitInterface::class.java)
        paymentService.searchPaymentList(roomId).enqueue(object : Callback<PaymentResponse>{
            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                val resp = response.body()!!
                when(resp.statusCode){
                    200 -> paymentView.onPaymentSearchSuccess(resp.list!!)
                    204 -> paymentView.onPaymentSearchNullSuccess()
                    else -> paymentView.onPaymentSearchFailure()
                }
            }
            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {

            }
        })
    }
}