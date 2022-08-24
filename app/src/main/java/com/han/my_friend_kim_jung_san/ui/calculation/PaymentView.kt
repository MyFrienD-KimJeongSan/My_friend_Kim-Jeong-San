package com.han.my_friend_kim_jung_san.ui.calculation

import com.han.my_friend_kim_jung_san.data.entity.Pay

interface PaymentView {
    fun onPaymentSearchSuccess(list: List<Pay>)
    fun onPaymentSearchNullSuccess()
    fun onPaymentSearchFailure()
}