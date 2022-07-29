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
import androidx.fragment.app.Fragment
import com.han.my_friend_kim_jung_san.databinding.FragmentHomeBinding
import jp.co.recruit_mp.android.lightcalendarview.LightCalendarView
import jp.co.recruit_mp.android.lightcalendarview.MonthView
import jp.co.recruit_mp.android.lightcalendarview.WeekDay
import jp.co.recruit_mp.android.lightcalendarview.accent.Accent
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    lateinit var fname: String
    lateinit var str: String
    lateinit var calendarView: CalendarView
    lateinit var updateBtn: Button
    lateinit var deleteBtn:Button
    lateinit var saveBtn:Button
    lateinit var diaryTextView: TextView
    lateinit var diaryContent:TextView
    lateinit var title:TextView
    lateinit var contextEditText: EditText

    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        var userID: String = "userID"
//
//        //토요일, 일요일 색깔 바뀌는 거 정정
//        binding.calendarView.setDayFilterColor(WeekDay.SUNDAY, Color.parseColor("#222b45"))
//        binding.calendarView.setWeekDayFilterColor(WeekDay.SUNDAY, Color.parseColor("#222b45"))
//
//        binding.calendarView.setWeekDayFilterColor(WeekDay.SATURDAY, Color.parseColor("#222b45"))
//        binding.calendarView.setDayFilterColor(WeekDay.SATURDAY, Color.parseColor("#222b45"))
//
//        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            diaryTextView.visibility = View.VISIBLE
//            saveBtn.visibility = View.VISIBLE
//            contextEditText.visibility = View.VISIBLE
//            diaryContent.visibility = View.INVISIBLE
//            updateBtn.visibility = View.INVISIBLE
//            deleteBtn.visibility = View.INVISIBLE
//            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
//            contextEditText.setText("")
//            checkDay(year, month, dayOfMonth, userID)
//        }
//
//        saveBtn.setOnClickListener {
//            saveDiary(fname)
//            contextEditText.visibility = View.INVISIBLE
//            saveBtn.visibility = View.INVISIBLE
//            updateBtn.visibility = View.VISIBLE
//            deleteBtn.visibility = View.VISIBLE
//            str = contextEditText.text.toString()
//            diaryContent.text = str
//            diaryContent.visibility = View.VISIBLE
//        }

//        binding.calendarView.setOnStateUpdatedListener(object : LightCalendarView.OnStateUpdatedListener {
//            override fun onDateSelected(date: Date) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onMonthSelected(date: Date, view: MonthView) {
//                val cal = Calendar.getInstance().apply { time = date }
////                val monthEvents: Map<Date, List<UsageEvents.Event>> = someApi.getMonthEvents(cal[Calendar.YEAR], cal[Calendar.MONTH])
////                val monthAccents: Map<Date, List<Accent>> = monthEvents.mapValues { event -> DotAccent(radius = 10f, color = event.color, key = event) }
////                view.setAccents(monthAccents)
//            }
//        })
        return binding.root
    }

    // 달력 내용 조회, 수정
//    fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {
//        //저장할 파일 이름설정
//        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"
//
//        var fileInputStream: FileInputStream
//        try {
//            fileInputStream = openFileInput(fname)
//            val fileData = ByteArray(fileInputStream.available())
//            fileInputStream.read(fileData)
//            fileInputStream.close()
//            str = String(fileData)
//            binding.contextEditText.visibility = View.INVISIBLE
//            binding.diaryContent.visibility = View.VISIBLE
//            binding.diaryContent.text = str
//            binding.saveBtn.visibility = View.INVISIBLE
//            binding.updateBtn.visibility = View.VISIBLE
//            binding.deleteBtn.visibility = View.VISIBLE
//            binding.updateBtn.setOnClickListener {
//                binding.contextEditText.visibility = View.VISIBLE
//                binding.diaryContent.visibility = View.INVISIBLE
//                binding.contextEditText.setText(str)
//                binding.saveBtn.visibility = View.VISIBLE
//                binding.updateBtn.visibility = View.INVISIBLE
//                binding.deleteBtn.visibility = View.INVISIBLE
//                binding.diaryContent.text = contextEditText.text
//            }
//            binding.deleteBtn.setOnClickListener {
//                binding.diaryContent.visibility = View.INVISIBLE
//                binding.updateBtn.visibility = View.INVISIBLE
//                binding.deleteBtn.visibility = View.INVISIBLE
//                binding.contextEditText.setText("")
//                binding.contextEditText.visibility = View.VISIBLE
//                binding.saveBtn.visibility = View.VISIBLE
//                removeDiary(fname)
//            }
//            if (binding.diaryContent.text == null) {
//                binding.diaryContent.visibility = View.INVISIBLE
//                binding.updateBtn.visibility = View.INVISIBLE
//                binding.deleteBtn.visibility = View.INVISIBLE
//                binding.diaryTextView.visibility = View.VISIBLE
//                binding.saveBtn.visibility = View.VISIBLE
//                binding.contextEditText.visibility = View.VISIBLE
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    // 달력 내용 제거
//    @SuppressLint("WrongConstant")
//    fun removeDiary(readDay: String?) {
//        var fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
//            val content = ""
//            fileOutputStream.write(content.toByteArray())
//            fileOutputStream.close()
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    // 달력 내용 추가
//    @SuppressLint("WrongConstant")
//    fun saveDiary(readDay: String?) {
//        var fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
//            val content = contextEditText.text.toString()
//            fileOutputStream.write(content.toByteArray())
//            fileOutputStream.close()
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
}


