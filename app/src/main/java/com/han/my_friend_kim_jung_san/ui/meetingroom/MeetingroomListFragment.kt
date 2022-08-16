package com.han.my_friend_kim_jung_san.ui.meetingroom

import androidx.fragment.app.Fragment
import com.han.my_friend_kim_jung_san.databinding.FragmentMeetingroomListBinding
import com.han.my_friend_kim_jung_san.databinding.FragmentOperationsBinding
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.calculation.FirstCalculationActivity
import com.han.my_friend_kim_jung_san.ui.calculation.SecondCalculationActivity
import com.han.my_friend_kim_jung_san.ui.mypage.MyPageActivity
import com.han.my_friend_kim_jung_san.ui.notice.NoticeActivity

class MeetingroomListFragment : BaseFragment<FragmentMeetingroomListBinding>(FragmentMeetingroomListBinding::inflate) {
    override fun initAfterBinding() {
        init()
    }

    private fun init(){
        binding.myMeetingList.setOnClickListener {
            startNextActivity(MeetingroomActivity::class.java)
        }
        binding.bellButton.setOnClickListener {
            startNextActivity(NoticeActivity::class.java)
        }
        binding.myPageButton.setOnClickListener {
            startNextActivity(MyPageActivity::class.java)
        }
    }
}