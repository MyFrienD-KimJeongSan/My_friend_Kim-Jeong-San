package com.han.my_friend_kim_jung_san.ui.calculation

import android.content.Intent
import com.han.my_friend_kim_jung_san.databinding.FragmentOperationsBinding
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.mypage.MyPageActivity
import com.han.my_friend_kim_jung_san.ui.notice.NoticeActivity


class OperationsFragment : BaseFragment<FragmentOperationsBinding>(FragmentOperationsBinding::inflate) {

    override fun initAfterBinding() {
        init()
    }
    private fun init(){
        binding.cardView1.setOnClickListener {
            startNextActivity(FirstCalculationActivity::class.java)
        }
        binding.cardView2.setOnClickListener {
            startNextActivity(SecondCalculationActivity::class.java)
        }
        binding.noticeBtn.setOnClickListener {
            startNextActivity(NoticeActivity::class.java)
        }
        binding.myPageButton.setOnClickListener {
            startNextActivity(MyPageActivity::class.java)
        }
    }
}