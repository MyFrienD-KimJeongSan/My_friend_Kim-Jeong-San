package com.han.my_friend_kim_jung_san.ui.chat

import android.view.View
import android.widget.PopupMenu
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivityMeetingroomBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment

class ChatActivity : BaseActivity<ActivityMeetingroomBinding>(ActivityMeetingroomBinding::inflate) {

    var isOpen : Boolean = false

    override fun initAfterBinding() {

        binding.menuOpenBtn.setOnClickListener {
            openMenu()
        }
        binding.menuCloseBtn.setOnClickListener {
            closeMenu()
        }

        openOptionMenu()
    }

    private fun openMenu() {
        if (isOpen == false) {
            binding.closeBottomMenuCL.visibility = View.GONE
            binding.openBottomMenuCL.visibility = View.VISIBLE
            isOpen = true
        }
    }

    private fun closeMenu() {
        if  (isOpen == true) {
            binding.openBottomMenuCL.visibility = View.GONE
            binding.closeBottomMenuCL.visibility = View.VISIBLE
            isOpen = false
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