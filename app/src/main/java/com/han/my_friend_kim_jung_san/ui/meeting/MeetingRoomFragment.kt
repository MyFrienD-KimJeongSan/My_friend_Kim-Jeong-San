package com.han.my_friend_kim_jung_san.ui.meeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.FragmentMeetingRoomBinding
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.chat.ChatActivity
import com.han.my_friend_kim_jung_san.ui.mypage.MyPageActivity
import com.han.my_friend_kim_jung_san.ui.notice.NoticeActivity


class MeetingRoomFragment : BaseFragment<FragmentMeetingRoomBinding>(FragmentMeetingRoomBinding::inflate) {
    override fun initAfterBinding() {
        init()
    }

    private fun init(){
        binding.myMeetingList.setOnClickListener {
            startNextActivity(ChatActivity::class.java)
        }
        binding.title.setOnClickListener {
            startNextActivity(ChatActivity::class.java)
        }
        binding.bellButton.setOnClickListener {
            startNextActivity(NoticeActivity::class.java)
        }
        binding.myPageButton.setOnClickListener {
            startNextActivity(MyPageActivity::class.java)
        }
    }
}