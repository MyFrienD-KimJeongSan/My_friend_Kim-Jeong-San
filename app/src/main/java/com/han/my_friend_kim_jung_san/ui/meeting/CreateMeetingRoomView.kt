package com.han.my_friend_kim_jung_san.ui.meeting

interface CreateMeetingRoomView {
    fun onCreateSuccess()
    fun onCreateFailure(code: Int?, message: String?)
}