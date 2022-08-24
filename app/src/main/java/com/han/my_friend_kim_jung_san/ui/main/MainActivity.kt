package com.han.my_friend_kim_jung_san.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.ui.account.AccountFragment
import com.han.my_friend_kim_jung_san.databinding.ActivityMainBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.account.SlideUpDialog
import com.han.my_friend_kim_jung_san.ui.calculation.OperationsFragment

import com.han.my_friend_kim_jung_san.ui.home.SelectedDayListener
import com.han.my_friend_kim_jung_san.ui.meeting.CreateMeetingActivity
import com.han.my_friend_kim_jung_san.ui.meeting.MeetingRoomFragment
import com.kakao.sdk.user.UserApiClient
import java.text.DecimalFormat
import java.time.LocalDate

@SuppressLint("NewApi")
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), SelectedDayListener {
    private var selectedDay: LocalDate? = null

    override fun initAfterBinding() {

        initNav()
    }
    private fun initNav(){
        binding.bottomNav.background = null
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commitAllowingStateLoss()

        binding.floatingButton.setOnClickListener {
            startActivityWithInfo(CreateMeetingActivity::class.java, "day", "${selectedDay?.year.toString()}.${String.format("%02d",selectedDay?.monthValue)}.${String.format("%02d", selectedDay?.dayOfMonth)}")
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    binding.floatingButton.setOnClickListener {
                        startActivityWithInfo(CreateMeetingActivity::class.java, "day", "${selectedDay?.year.toString()}.${String.format("%02d",selectedDay?.monthValue)}.${String.format("%02d", selectedDay?.dayOfMonth)}")
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.operations -> {
                    binding.floatingButton.setOnClickListener {
                        Toast.makeText(applicationContext,"oper", Toast.LENGTH_SHORT).show()
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, OperationsFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.chat -> {
                    binding.floatingButton.setOnClickListener {
                        showToast("chat")
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MeetingRoomFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.account -> {
                    binding.floatingButton.setOnClickListener {
                        onSlideUpDialog()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AccountFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    @SuppressLint("InflateParams")
    private fun onSlideUpDialog() {
        val contentView: View = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.add_account_popup, null)
        val slideupPopup = SlideUpDialog.Builder(this)
            .setContentView(contentView)
            .create()
        slideupPopup.show()
    }

    override fun selectedDay(date: LocalDate) {
        selectedDay = date
    }

}