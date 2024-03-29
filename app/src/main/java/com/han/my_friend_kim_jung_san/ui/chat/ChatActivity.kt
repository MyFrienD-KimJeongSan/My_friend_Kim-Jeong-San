package com.han.my_friend_kim_jung_san.ui.chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.PopupMenu

import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.han.my_friend_kim_jung_san.ApplicationClass.Companion.BASE_URL
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.data.entity.Pay
import com.han.my_friend_kim_jung_san.data.local.MenuData
import com.han.my_friend_kim_jung_san.data.remote.payment.PaymentService
import com.han.my_friend_kim_jung_san.databinding.ActivityChatBinding
import com.han.my_friend_kim_jung_san.databinding.VoteMenuItemBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.calculation.*
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment
import com.han.my_friend_kim_jung_san.ui.main.MainActivity
import com.han.my_friend_kim_jung_san.ui.meeting.MeetingRoomFragment
import java.util.*

class ChatActivity : BaseActivity<ActivityChatBinding>(ActivityChatBinding::inflate), PaymentView {


    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val CAMERA_CODE = 98

    var isOpen : Boolean = false

    override fun initAfterBinding() {
        init()

        openOptionMenu()
    }


    private var name = ""
    private var startDate = ""
    private var roomId = 0
    private var userIdList = arrayListOf<String>()
    private var userNameList = arrayListOf<String>()
    @SuppressLint("SetTextI18n")
    private fun init(){
        name = this.intent.getStringExtra("name")!!
        startDate = this.intent.getStringExtra("startDate")!!
        roomId = this.intent.getIntExtra("roomId", 0)
        userIdList = this.intent.getStringArrayListExtra("userIdList")!!
        userNameList = this.intent.getStringArrayListExtra("userNameList")!!

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
        userNameList.let {
            for(i in 1..userNameList.lastIndex){
                remainUser += "${userNameList[i]}님,"
            }
            remainUser = remainUser.dropLast(1)
            binding.chatUserListTV.text = "${userNameList[0]}님이 ${remainUser}을 초대했습니다."
        }
        binding.backArrowIBtn.setOnClickListener {
            finish()
        }

        binding.bottomMenuN1.setOnClickListener {
            searchPayment(roomId, 1)
        }

        binding.bottomMenuDI.setOnClickListener {
            searchPayment(roomId, 2)
        }

        binding.bottomMenuCamera.setOnClickListener {
            CallCamera()
        }
        openOptionMenu()

        val list = ArrayList<MenuData>()
        list.add(MenuData(false, "소주", "5,000", "1", "5,000", "2"))
        list.add(MenuData(false, "삼겹살", "12,500", "3", "37,500", "2"))
        list.add(MenuData(false, "짬뽕탕", "15,000", "1", "15,000", "2"))

        val adapter = VoteMenuRVAdapter(list)
        binding.menuList.adapter = adapter

        var isClicked: Boolean = false


        binding.voteCompleteBtn.setOnClickListener {
            binding.voteCompleteBtn.isActivated = !binding.voteCompleteBtn.isActivated
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
            if (isOpen) {
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

        fun checkPermission(permissions: Array<out String>): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (permission in permissions) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(this, permissions, CAMERA_CODE)
                        return false;
                    }
                }
            }

            return true;
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                CAMERA_CODE -> {
                    for (grant in grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "카메라 권한을 승인해 주세요.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        fun CallCamera() {
            if (checkPermission(CAMERA)) {
                val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(itt, CAMERA_CODE)
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    CAMERA_CODE -> {
                        if (data?.extras?.get("data") != null) {
                            val img = data?.extras?.get("data") as Bitmap
                            binding.imageView.setImageBitmap(img)
                        }
                    }
                }
            }
        }



    private fun searchPayment(roomId: Int, id: Int) {
        PaymentService.searchPaymentList(this, roomId, id)
    }

    //정산 목록이 있을 때
    override fun onPaymentSearchSuccess(list: List<Pay>, id: Int) {
        Log.i("payment", "정산목록 있을 때")
        val intent = Intent(this, OperationListActivity::class.java)
        intent.putExtra("roomId", roomId)
        intent.putExtra("startDate", startDate)
        intent.putExtra("userIdList", userIdList)
        intent.putExtra("userNameList", userNameList)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    //정산 목록이 없을 때
    override fun onPaymentSearchNullSuccess(id: Int) {
        Log.i("payment", "정산목록 없을 때")
        if (id == 1){
            val intent = Intent(this, MeetFirstCalcActivity::class.java)
            intent.putExtra("roomId", roomId)
            intent.putExtra("startDate", startDate)
            intent.putExtra("userIdList", userIdList)
            intent.putExtra("userNameList", userNameList)
            intent.putExtra("id", id)
            startActivity(intent)
        }else if(id == 2){
            val intent = Intent(this, MeetSecondCalcActivity::class.java)
            intent.putExtra("roomId", roomId)
            intent.putExtra("startDate", startDate)
            intent.putExtra("userIdList", userIdList)
            intent.putExtra("userNameList", userNameList)
            intent.putExtra("id", id)
            startActivity(intent)
        }


    }

    override fun onPaymentSearchFailure() {
    }
}