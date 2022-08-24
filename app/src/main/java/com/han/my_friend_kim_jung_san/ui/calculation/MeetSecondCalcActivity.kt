package com.han.my_friend_kim_jung_san.ui.calculation

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.han.my_friend_kim_jung_san.databinding.ActivityMeetSecondCalcBinding
import com.han.my_friend_kim_jung_san.ui.BaseActivity

class MeetSecondCalcActivity : BaseActivity<ActivityMeetSecondCalcBinding>(ActivityMeetSecondCalcBinding::inflate) {

    override fun initAfterBinding() {
        val payers = arrayOf("김민지", "김유진", "박서연")
        val payerSpinner : Spinner = binding.payerSpinner
        val adapter : ArrayAdapter<String> = ArrayAdapter(this, R.layout.simple_list_item_1, payers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        payerSpinner.adapter = adapter
    }
}