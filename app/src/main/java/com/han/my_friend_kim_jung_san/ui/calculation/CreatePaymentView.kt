package com.han.my_friend_kim_jung_san.ui.calculation

import com.han.my_friend_kim_jung_san.data.entity.Pay

interface CreatePaymentView {
    fun onPaymentCreateSuccess()
    fun onPaymentCreateFailure()
}