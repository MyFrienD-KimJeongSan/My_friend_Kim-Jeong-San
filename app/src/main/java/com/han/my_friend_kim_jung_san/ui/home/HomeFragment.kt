package com.han.my_friend_kim_jung_san.ui.home


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.data.entity.Data
import com.han.my_friend_kim_jung_san.data.entity.User
import com.han.my_friend_kim_jung_san.data.remote.room.RoomService
import com.han.my_friend_kim_jung_san.databinding.CalendarDayBinding
import com.han.my_friend_kim_jung_san.databinding.CalendarHeaderBinding
import com.han.my_friend_kim_jung_san.databinding.FragmentHomeBinding
import com.han.my_friend_kim_jung_san.databinding.MeetingItemBinding
import com.han.my_friend_kim_jung_san.extensions.*
import com.han.my_friend_kim_jung_san.extensions.layoutInflater
import com.han.my_friend_kim_jung_san.extensions.setTextColorRes
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.CalendarFragment
import com.han.my_friend_kim_jung_san.ui.chat.ChatActivity
import com.han.my_friend_kim_jung_san.ui.mypage.MyPageActivity
import com.han.my_friend_kim_jung_san.ui.notice.NoticeActivity
import com.kakao.sdk.user.UserApiClient
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import java.lang.RuntimeException
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*


class EventsAdapter(val onClick: (Data) -> Unit) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    val events = mutableListOf<Data>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsAdapter.EventsViewHolder {
        return EventsViewHolder(
            MeetingItemBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventsAdapter.EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class EventsViewHolder(private val binding: MeetingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }

        //todo 변경해야함
        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            when(data.color){
                "red" -> binding.meetCategoryIV.setImageResource(R.drawable.red)
                "orange" -> binding.meetCategoryIV.setImageResource(R.drawable.orange)
                "yellow" -> binding.meetCategoryIV.setImageResource(R.drawable.yellow)
                "green" -> binding.meetCategoryIV.setImageResource(R.drawable.green)
                "blue" -> binding.meetCategoryIV.setImageResource(R.drawable.blue)
                "purple" -> binding.meetCategoryIV.setImageResource(R.drawable.purple)
            }
            binding.meetingTimeTV.text = "${data.startDate} ${data.startTime}"
            binding.meetingTitleTV.text = data.name

            var member = ""
            data.userList?.forEach {
                member += "${it.name},"
            }
            member.dropLast(1)
            binding.meetingMemberTV.text = member
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : CalendarFragment(R.layout.fragment_home), SearchView {
    private val eventsAdapter = EventsAdapter {
        val intent = Intent(context, ChatActivity::class.java)
        startActivity(intent)
    }
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val events = mutableMapOf<LocalDate, List<Data>>()
    private lateinit var binding: FragmentHomeBinding
    private var selectedDayListener: SelectedDayListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.meetingRoomRV.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.calendar.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }
        if (savedInstanceState == null) {
            binding.calendar.post {
                selectDate(today)
                selectedDayListener!!.selectedDay(today)
            }
        }
        binding.noticeBtn.setOnClickListener {
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }
        binding.myPageButton.setOnClickListener {
            val intent = Intent(context, MyPageActivity::class.java)
            startActivity(intent)
        }
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                        selectedDayListener!!.selectedDay(day.date)

                        //일정 요청
                        searchSchedule()
                    }
                }
            }
        }
        binding.calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.dayTV
                val red = container.binding.red
                val orange = container.binding.orange
                val yellow = container.binding.yellow
                val green = container.binding.green
                val blue = container.binding.blue
                val purple = container.binding.purple

                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {

                        selectedDate -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.selected_day)
                            //todo 색깔별로 처리해야함
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                            events[day.date].orEmpty().forEach {
                                when(it.color){
                                    "red" -> red.makeVisible()
                                    "orange" -> orange.makeVisible()
                                    "yellow" -> yellow.makeVisible()
                                    "green" -> green.makeVisible()
                                    "blue" -> blue.makeVisible()
                                    "purple" -> purple.makeVisible()
                                }
                            }
                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.gray_text_color)
                    red.makeInVisible()
                    orange.makeInVisible()
                    yellow.makeInVisible()
                    green.makeInVisible()
                    blue.makeInVisible()
                    purple.makeInVisible()
                }
            }
        }
        binding.calendar.monthScrollListener = {
            binding.yearTV.text = it.yearMonth.year.toString()
            binding.monthTV.text = monthTitleFormatter.format(it.yearMonth)

        }
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }
        binding.calendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.text = daysOfWeek[index].name.first().toString()
                            tv.setTextColorRes(R.color.black)
                        }
                }
            }

            override fun create(view: View) = MonthViewContainer(view)
        }
        binding.rightArrowIV.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }
        binding.leftArrowIV.setOnClickListener{
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendar.notifyDateChanged(it) }
            binding.calendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.apply {
            events.clear()
            events.addAll(this@HomeFragment.events[date].orEmpty())
            notifyDataSetChanged()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SelectedDayListener){
            selectedDayListener = context
        }else{
            throw RuntimeException(context.toString())
        }
    }
    override fun onDetach() {
        super.onDetach()
        selectedDayListener = null
    }

    override fun onSearchSuccess(data: List<Data>?) {
        Log.i("search", "검색 성공")
        //일정 검색 성공했을 경우
        data?.forEach { data ->
            selectedDate?.let {
                events[it] = events[it].orEmpty().plus(data)
                updateAdapterForDate(it)
            }
        }
    }

    override fun onSearchFailure(code: Int?, message: String?) {
        Log.i("search", "$code $message 검색 실패")
    }
    private fun searchSchedule(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("user", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("user", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                RoomService.searchRoom(this, "${user.id}", selectedDate.toString())
            }
        }

    }
}


