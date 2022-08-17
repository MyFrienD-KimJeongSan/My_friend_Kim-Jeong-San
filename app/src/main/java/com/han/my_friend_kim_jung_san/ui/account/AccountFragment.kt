package com.han.my_friend_kim_jung_san.ui.account


import com.han.my_friend_kim_jung_san.databinding.FragmentAccountBinding
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.notice.NoticeActivity
import com.han.my_friend_kim_jung_san.ui.mypage.MyPageActivity


class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {
    override fun initAfterBinding() {
        init()
    }
    private fun init(){
        binding.noticeBtn.setOnClickListener {
            startNextActivity(NoticeActivity::class.java)
        }
        binding.myPageButton.setOnClickListener {
            startNextActivity(MyPageActivity::class.java)
        }
    }

}