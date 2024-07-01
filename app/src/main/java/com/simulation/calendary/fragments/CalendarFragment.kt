package com.simulation.calendary.fragments

import com.simulation.calendary.viewmodels.CalendarViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simulation.calendary.R
import com.simulation.calendary.adapters.CalendarAdapter
import com.simulation.calendary.models.TaskDetail
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var startDate: LocalDate
    private lateinit var month: TextView
    private var btnNextMonth: Button? = null
    private var btnPrevMonth: Button? = null
    private lateinit var etTaskTitle: TextView
    private lateinit var etTaskDescription: TextView
    private lateinit var btnAddTask: Button
    private lateinit var numberOfDays: List<String>
    private lateinit var addTaskLayout: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        month = view.findViewById(R.id.tvCurrentMonth)
        calendarAdapter = CalendarAdapter(emptyList(), viewModel.selectedDate.value?.dayOfMonth ?: -1)
        startDate = viewModel.selectedDate.value ?: LocalDate.now()
        setupViews(view)
        setMonth()
        setupListeners()
        setupObservers()
    }

    private fun setMonth() {
        addTaskLayout.visibility = View.GONE
        month.text = getMonthFromDate(startDate)
        numberOfDays = getNumberOfDays(startDate)
        calendarAdapter.updateData(numberOfDays)
    }

    private fun getMonthFromDate(date: LocalDate): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(dateTimeFormatter)
    }

    private fun getNumberOfDays(date: LocalDate): List<String> {
        val arr = mutableListOf<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = LocalDate.of(date.year, date.month, 1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                arr.add("")
            } else {
                val day = (i - dayOfWeek).toString()
                arr.add(day)
            }
        }
        return arr.toList()
    }

    private fun setupViews(view: View) {
        btnNextMonth = view.findViewById(R.id.btnNextMonth)
        btnPrevMonth = view.findViewById(R.id.btnPrevMonth)
        etTaskTitle = view.findViewById(R.id.etTaskTitle)
        etTaskDescription = view.findViewById(R.id.etTaskDescription)
        btnAddTask = view.findViewById(R.id.btnAddTask)
        addTaskLayout = view.findViewById(R.id.layoutAddTask)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvCalendar)
        recyclerView.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(activity, 7)
        }
    }

    private fun setupListeners() {
        btnNextMonth?.setOnClickListener {
            startDate = startDate.plusMonths(1)
            viewModel.setSelectedDate(startDate)
            setMonth()
        }

        btnPrevMonth?.setOnClickListener {
            startDate = startDate.minusMonths(1)
            viewModel.setSelectedDate(startDate)
            setMonth()
        }

        calendarAdapter.onItemClick = { day ->
            val yearMonth = YearMonth.from(startDate)
            val daysInMonth = yearMonth.lengthOfMonth()

            if (day in 1..daysInMonth) { // Check if the selected day is valid for the current month
                val selectedDate = startDate.withDayOfMonth(day)
                Log.d("onClick", "Selected day: $selectedDate")
                viewModel.setSelectedDate(selectedDate)
            }

            addTaskLayout.visibility = View.VISIBLE
        }

        btnAddTask.setOnClickListener {
            val title = etTaskTitle.text.toString()
            val description = etTaskDescription.text.toString()
            val task = TaskDetail(title, description)
            viewModel.storeCalendarTask(task)
            Toast.makeText(activity,"Task added to calendar",Toast.LENGTH_SHORT).show()
            addTaskLayout.visibility = View.GONE
        }
    }


    private fun setupObservers() {
        viewModel.selectedDate.observe(viewLifecycleOwner) { newDate ->
            startDate = newDate
            setMonth()
            calendarAdapter.setSelectedDate(newDate.dayOfMonth)
        }
    }
}
