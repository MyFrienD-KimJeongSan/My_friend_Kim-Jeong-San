package com.han.my_friend_kim_jung_san.ui.meeting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.databinding.ActivityCreateMeetingBinding
import com.han.my_friend_kim_jung_san.extensions.makeInVisible
import com.han.my_friend_kim_jung_san.extensions.makeVisible
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class CreateMeetingActivity: BaseActivity<ActivityCreateMeetingBinding>(ActivityCreateMeetingBinding::inflate) {
    var stateColor: String? = null
    override fun initAfterBinding() {
        binding.dayTV.text = this.intent.getStringExtra("day")

        binding.backArrowIBtn.setOnClickListener {
            finish()
        }
        binding.stateRedIBtn.setOnClickListener {
            stateColor = "red"
            binding.stateColorIV.makeVisible()
            changeBackGroundColor(stateColor)
        }
        binding.stateOrangeIBtn.setOnClickListener {
            stateColor = "orange"
            binding.stateColorIV.makeVisible()
            changeBackGroundColor(stateColor)
        }
        binding.stateYellowIBtn.setOnClickListener {
            stateColor = "yellow"
            binding.stateColorIV.makeVisible()
            changeBackGroundColor(stateColor)
        }
        binding.stateGreenIBtn.setOnClickListener {
            stateColor = "green"
            binding.stateColorIV.makeVisible()
            changeBackGroundColor(stateColor)
        }
        binding.stateBlueIBtn.setOnClickListener {
            stateColor = "blue"
            binding.stateColorIV.makeVisible()
            changeBackGroundColor(stateColor)
        }
        binding.statePurpleIBtn.setOnClickListener {
            stateColor = "purple"
            binding.stateColorIV.makeVisible()
            changeBackGroundColor(stateColor)
        }

    }

    private fun changeBackGroundColor(color: String?){
        when(color){
            "red" -> {
                binding.stateColorIV.setImageResource(R.drawable.red)
                binding.stateRedIBtn.setBackgroundResource(R.drawable.selected_button)
                binding.stateOrangeIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateYellowIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateGreenIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateBlueIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.statePurpleIBtn.setBackgroundResource(R.drawable.unselected_button)
            }
            "orange" -> {
                binding.stateColorIV.setImageResource(R.drawable.orange)
                binding.stateRedIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateOrangeIBtn.setBackgroundResource(R.drawable.selected_button)
                binding.stateYellowIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateGreenIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateBlueIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.statePurpleIBtn.setBackgroundResource(R.drawable.unselected_button)
            }
            "yellow" -> {
                binding.stateColorIV.setImageResource(R.drawable.yellow)
                binding.stateRedIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateOrangeIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateYellowIBtn.setBackgroundResource(R.drawable.selected_button)
                binding.stateGreenIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateBlueIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.statePurpleIBtn.setBackgroundResource(R.drawable.unselected_button)
            }
            "green" -> {
                binding.stateColorIV.setImageResource(R.drawable.green)
                binding.stateRedIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateOrangeIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateYellowIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateGreenIBtn.setBackgroundResource(R.drawable.selected_button)
                binding.stateBlueIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.statePurpleIBtn.setBackgroundResource(R.drawable.unselected_button)
            }
            "blue" -> {
                binding.stateColorIV.setImageResource(R.drawable.blue)
                binding.stateRedIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateOrangeIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateYellowIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateGreenIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateBlueIBtn.setBackgroundResource(R.drawable.selected_button)
                binding.statePurpleIBtn.setBackgroundResource(R.drawable.unselected_button)
            }
            "purple" -> {
                binding.stateColorIV.setImageResource(R.drawable.purple)
                binding.stateRedIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateOrangeIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateYellowIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateGreenIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.stateBlueIBtn.setBackgroundResource(R.drawable.unselected_button)
                binding.statePurpleIBtn.setBackgroundResource(R.drawable.selected_button)
            }
        }
    }
}