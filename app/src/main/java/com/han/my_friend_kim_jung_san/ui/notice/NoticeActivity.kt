package com.han.my_friend_kim_jung_san.ui.notice

import com.han.my_friend_kim_jung_san.databinding.ActivityNoticeBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(ActivityNoticeBinding::inflate) {
    override fun initAfterBinding() {
        binding.backArrowIBtn.setOnClickListener {
            finish()
        }
    }
}