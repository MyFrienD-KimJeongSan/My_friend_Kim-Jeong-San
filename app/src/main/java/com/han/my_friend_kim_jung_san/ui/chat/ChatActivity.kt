package com.han.my_friend_kim_jung_san.ui.chat

import android.annotation.SuppressLint
import android.view.View
import android.widget.PopupMenu
import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.BASE_URL
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivityChatBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment

class ChatActivity : BaseActivity<ActivityChatBinding>(ActivityChatBinding::inflate) {
    var isOpen : Boolean = false
    override fun initAfterBinding() {
        init()

        openOptionMenu()
    }

    private fun init(){
        val name = this.intent.getStringExtra("name")
        val startDate = this.intent.getStringExtra("startDate")
        val roomId = this.intent.getIntExtra("roomId", 0)
        val userIdList = this.intent.getStringArrayListExtra("userIdList")
        val userNameList = this.intent.getStringArrayListExtra("userNameList")
        binding.menuOpenBtn.setOnClickListener {
            openMenu()
        }
        binding.menuCloseBtn.setOnClickListener {
            closeMenu()
        }
        binding.roomTitle.text = name
        //임시로 날짜 넣음
        binding.chatStartTV.text = startDate
        var remainUser = ""
        userNameList?.let {
            for(i in 1..userNameList.lastIndex){
                remainUser += "${userNameList[i]}님,"
            }
            remainUser = remainUser.dropLast(1)
            binding.chatUserListTV.text = "${userNameList[0]}님이 ${remainUser}을 초대했습니다."
        }


        binding.bottomMenuN1.setOnClickListener {

        }
    }

    private fun openMenu() {
        if (!isOpen) {
            binding.closeBottomMenuCL.visibility = View.GONE
            binding.openBottomMenuCL.visibility = View.VISIBLE
            isOpen = true
        }
    }

    private fun closeMenu() {
        if  (isOpen) {
            binding.openBottomMenuCL.visibility = View.GONE
            binding.closeBottomMenuCL.visibility = View.VISIBLE
            isOpen = false
        }
    }

    @SuppressLint("NewApi")
    private fun openOptionMenu() {
        binding.optionBtn.setOnClickListener {
            var popupMenu = PopupMenu(applicationContext, it)

            menuInflater.inflate(R.menu.option_menu, popupMenu.menu)
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