package com.han.my_friend_kim_jung_san.data.remote.payment

import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.retrofit
import com.han.my_friend_kim_jung_san.data.entity.Payment
import com.han.my_friend_kim_jung_san.ui.calculation.CreatePaymentView
import com.han.my_friend_kim_jung_san.ui.calculation.PaymentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PaymentService {
    val paymentService = retrofit.create(PaymentRetrofitInterface::class.java)
    fun searchPaymentList(paymentView: PaymentView, roomId: Int){
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
    fun createPayment(createPaymentView: CreatePaymentView, payment: Payment){
        paymentService.createPayment(payment).enqueue(object : Callback<CreatePaymentResponse>{
            override fun onResponse(
                call: Call<CreatePaymentResponse>,
                response: Response<CreatePaymentResponse>
            ) {
                val resp = response.body()!!
                when(resp.statusCode){
                    400 -> createPaymentView.onPaymentCreateSuccess()
                    else -> createPaymentView.onPaymentCreateFailure()
                }
            }

            override fun onFailure(call: Call<CreatePaymentResponse>, t: Throwable) {
            }
        })
    }
}