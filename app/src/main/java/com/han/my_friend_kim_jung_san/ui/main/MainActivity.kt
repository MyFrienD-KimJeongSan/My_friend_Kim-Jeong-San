package com.han.my_friend_kim_jung_san.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.han.my_friend_kim_jung_san.ui.home.HomeFragment
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.ui.account.AccountFragment
import com.han.my_friend_kim_jung_san.databinding.ActivityMainBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity
import com.han.my_friend_kim_jung_san.ui.account.SlideUpDialog
import com.han.my_friend_kim_jung_san.ui.calculation.OperationsFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun initAfterBinding() {
        initNav()
    }
    private fun initNav(){
        binding.bottomNav.background = null
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commitAllowingStateLoss()

        binding.floatingButton.setOnClickListener {
            Toast.makeText(applicationContext,"home", Toast.LENGTH_SHORT).show()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    binding.floatingButton.setOnClickListener {
                        Toast.makeText(applicationContext,"home", Toast.LENGTH_SHORT).show()
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
        var contentView: View = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.add_account_popup, null)
        val slideupPopup = SlideUpDialog.Builder(this)
            .setContentView(contentView)
            .create()
        slideupPopup.show()
    }


}