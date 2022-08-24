package com.han.my_friend_kim_jung_san.ui.home


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.my_friend_kim_jung_san.R
import com.han.my_friend_kim_jung_san.data.entity.Data
import com.han.my_friend_kim_jung_san.data.remote.room.RoomService
import com.han.my_friend_kim_jung_san.databinding.CalendarDayBinding
import com.han.my_friend_kim_jung_san.databinding.CalendarHeaderBinding
import com.han.my_friend_kim_jung_san.databinding.FragmentHomeBinding
import com.han.my_friend_kim_jung_san.databinding.MeetingItemBinding
import com.han.my_friend_kim_jung_san.extensions.*
import com.han.my_friend_kim_jung_san.extensions.layoutInflater
import com.han.my_friend_kim_jung_san.extensions.setTextColorRes
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
            member = member.dropLast(1)
            binding.meetingMemberTV.text = member
        }
    }
}

@SuppressLint("NewApi")
class HomeFragment : CalendarFragment(R.layout.fragment_home), SearchView, AllSearchView {
    private val eventsAdapter = EventsAdapter {
        val intent = Intent(activity, ChatActivity::class.java)
        intent.putExtra("roomId", it.roomId!!.toInt())
        intent.putExtra("name", it.name.toString())
        intent.putExtra("startDate", it.startDate.toString())
        val userIdList = arrayListOf<String>() //userid, name
        val userNameList = arrayListOf<String>()
        it.userList.orEmpty().forEach { user ->
            userIdList.add(user.userId.toString())
            userNameList.add(user.name.toString())
        }
        intent.putExtra("userIdList", userIdList)
        intent.putExtra("userNameList", userNameList)

        startActivity(intent)
    }
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    val event = mutableMapOf<String, List<Data>>()
    private lateinit var binding: FragmentHomeBinding
    private var selectedDayListener: SelectedDayListener? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        binding.meetingRoomRV.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
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
                searchSchedule()
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


        binding.calendar.monthScrollListener = { month ->
            binding.yearTV.text = month.yearMonth.year.toString()
            binding.monthTV.text = monthTitleFormatter.format(month.yearMonth)

        }
        //allSearch()
        Log.i("event", "$event 1")
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayBinding.bind(view)

            init {

                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        //일정요청
                        searchSchedule()
                        selectDate(day.date)
                        selectedDayListener!!.selectedDay(day.date)
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
                            event[day.date.toString()].orEmpty().forEach {
                                when(it.color){
                                    "red" -> red.makeVisible()
                                    "yellow" -> yellow.makeVisible()
                                    "orange" -> orange.makeVisible()
                                    "green" -> green.makeVisible()
                                    "blue" -> blue.makeVisible()
                                    "purple" -> purple.makeVisible()
                                }
                            }
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                            Log.i("event", "$event 2")
                            event[day.date.toString()].orEmpty().forEach {
                                when(it.color){
                                    "red" -> red.makeVisible()
                                    "yellow" -> yellow.makeVisible()
                                    "orange" -> orange.makeVisible()
                                    "green" -> green.makeVisible()
                                    "blue" -> blue.makeVisible()
                                    "purple" -> purple.makeVisible()
                                }
                            }
                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.gray_text_color)
                }
            }
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
            updateAdapterForDate(date.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapterForDate(date: String?) {
        eventsAdapter.apply {
            events.clear()
            events.addAll(event[date].orEmpty().distinct())
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
        //Log.i("search", "검색 성공 ${data.toString()}")
        //일정 검색 성공했을 경우
        data?.forEach { list ->
            Log.i("search", list.toString())

            event[list.startDate.toString()] = event[list.startDate.toString()].orEmpty().plus(list)
            updateAdapterForDate(list.startDate.toString())

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

    private fun allSearch(){

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
                RoomService.allSearchRoom(this, user.id.toString())
            }
        }
    }
    //all search
    override fun onAllSearchSuccess(data: List<Data>?) {

        data?.forEach { list ->
            Log.i("search123", list.toString())
            event[list.startDate.toString()] = event[list.startDate.toString()].orEmpty().plus(list)
            updateAdapterForDate(list.startDate.toString())
        }

    }

    override fun onAllSearchFailure(code: Int?, message: String?) {
    }
}


