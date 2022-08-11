package com.han.my_friend_kim_jung_san

import android.annotation.SuppressLint
import android.app.usage.UsageEvents
import android.content.Context
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.han.my_friend_kim_jung_san.databinding.FragmentCalendarBinding
import jp.co.recruit_mp.android.lightcalendarview.LightCalendarView
import jp.co.recruit_mp.android.lightcalendarview.MonthView
import jp.co.recruit_mp.android.lightcalendarview.WeekDay
import jp.co.recruit_mp.android.lightcalendarview.accent.Accent
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    lateinit var binding: FragmentCalendarBinding
    private lateinit var calendar : Calendar

    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)


        //토요일, 일요일 색깔 바뀌는 거 정정
        binding.calendarView.setDayFilterColor(WeekDay.SUNDAY, Color.parseColor("#222b45"))
        binding.calendarView.setWeekDayFilterColor(WeekDay.SUNDAY, Color.parseColor("#222b45"))

        binding.calendarView.setWeekDayFilterColor(WeekDay.SATURDAY, Color.parseColor("#222b45"))
        binding.calendarView.setDayFilterColor(WeekDay.SATURDAY, Color.parseColor("#222b45"))


        return binding.root
    }
}


