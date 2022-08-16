package com.han.my_friend_kim_jung_san.ui.meetingroom

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.marginBottom
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivityMainBinding
import com.han.my_friend_kim_jung_san.databinding.ActivityMeetingroomBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment

class MeetingroomActivity : BaseActivity<ActivityMeetingroomBinding>(ActivityMeetingroomBinding::inflate) {

    override fun initAfterBinding() {

        openMenu()
        closeMenu()

    }

    private fun openMenu() {
        binding.menuOpenBtn.setOnClickListener {
            binding.closeBottomMenuCL.visibility = View.GONE
            binding.openBottomMenuCL.visibility = View.VISIBLE
        }
    }

    private fun closeMenu() {
        binding.menuOpenBtn.setOnClickListener {
            binding.openBottomMenuCL.visibility = View.GONE
            binding.closeBottomMenuCL.visibility = View.VISIBLE
        }
    }

    private fun openOptionMenu() {
        binding.optionBtn.setOnClickListener {
            var popupMenu = PopupMenu(applicationContext, it)

            menuInflater?.inflate(R.menu.option_menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
//                    R.id.finalOperation -> {
//                        //최종 정산 내역 프래그먼트와 연결
//                    }

                    R.id.getOut -> {

                        //대화방 나가기 기능과 연결

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, HomeFragment())
                            .commitAllowingStateLoss()
                        return@setOnMenuItemClickListener true
                    }
                }
                false
            }
        }
    }
}