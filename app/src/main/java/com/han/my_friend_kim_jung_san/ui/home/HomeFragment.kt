package com.han.my_friend_kim_jung_san.ui.home


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
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
import com.han.my_friend_kim_jung_san.databinding.CalendarDayBinding
import com.han.my_friend_kim_jung_san.databinding.CalendarHeaderBinding
import com.han.my_friend_kim_jung_san.databinding.FragmentHomeBinding
import com.han.my_friend_kim_jung_san.databinding.MeetingItemBinding
import com.han.my_friend_kim_jung_san.extensions.*
import com.han.my_friend_kim_jung_san.extensions.layoutInflater
import com.han.my_friend_kim_jung_san.extensions.setTextColorRes
import com.han.my_friend_kim_jung_san.ui.BaseFragment
import com.han.my_friend_kim_jung_san.ui.CalendarFragment
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

data class Event(val id: String, val text: String, val date: LocalDate)

class EventsAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    val events = mutableListOf<Event>()
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
        fun bind(event: Event) {
            binding.meetingTitleTV.text = event.text
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : CalendarFragment(R.layout.fragment_home) {
    private val eventsAdapter = EventsAdapter {
        Toast.makeText(context, "이벤트", Toast.LENGTH_SHORT).show()
    }
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val events = mutableMapOf<LocalDate, List<Event>>()
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
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
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
                val blueDotView = container.binding.blueDot
                val skyBlueDotView = container.binding.skyBlueDot
                val greenDotView = container.binding.greenDot

                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {

                        selectedDate -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.selected_day)
                            //todo 색깔별로 처리해야함
                            blueDotView.makeInVisible()

                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                            blueDotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.gray_text_color)
                    blueDotView.makeInVisible()
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
}


