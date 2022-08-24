package com.han.my_friend_kim_jung_san.ui.chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.data.local.MenuData
import com.han.my_friend_kim_jung_san.databinding.ActivityChatBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.calculation.FirstCalculationActivity
import com.han.my_friend_kim_jung_san.ui.calculation.SecondCalculationActivity
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment
import com.han.my_friend_kim_jung_san.ui.main.MainActivity
import com.han.my_friend_kim_jung_san.ui.meeting.MeetingRoomFragment
import java.util.*

class ChatActivity : BaseActivity<ActivityChatBinding>(ActivityChatBinding::inflate) {

    var isOpen : Boolean = false
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val CAMERA_CODE = 98

    override fun initAfterBinding() {

        binding.backArrowIBtn.setOnClickListener {
            finish()
        }

        binding.menuOpenBtn.setOnClickListener {
            openMenu()
        }

        binding.menuCloseBtn.setOnClickListener {
            closeMenu()
        }

        binding.bottomMenuDI.setOnClickListener {
            startNextActivity(FirstCalculationActivity::class.java)
        }

        binding.bottomMenuDI.setOnClickListener {
            startNextActivity(SecondCalculationActivity::class.java)
        }

        binding.bottomMenuCamera.setOnClickListener {
            CallCamera()
        }
        openOptionMenu()

        val list = ArrayList<MenuData>()
        list.add(MenuData("소주", "5,000", "1", "5,000", "2"))
        list.add(MenuData("삼겹살", "12,500", "3", "37,500", "2"))
        list.add(MenuData("짬뽕탕", "15,000", "1", "15,000", "2"))

        val adapter = VoteMenuRVAdapter(list)
        binding.menuList.adapter = adapter
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

    @SuppressLint("NewApi")
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

    fun checkPermission(permissions: Array<out String>): Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, CAMERA_CODE)
                    return false;
                }
            }
        }

        return true;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_CODE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "카메라 권한을 승인해 주세요.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun CallCamera()
    {
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

}