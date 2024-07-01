package com.simulation.calendary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.simulation.calendary.R

class CalendarAdapter(private var daysOfMonth: List<String>, private var selectedDate: Int): RecyclerView.Adapter<CalendarAdapter.DateViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    inner class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dayText: TextView = itemView.findViewById(R.id.cellDayText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DateViewHolder(layoutInflater.inflate(R.layout.calendar_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val day = daysOfMonth[position]
        if (day.isNotEmpty()) {
            holder.dayText.text = day
            if (position + 1 == selectedDate) { // Adjusted to match 1-based selectedDate
                holder.itemView.setBackgroundResource(R.drawable.border)
            } else {
                holder.dayText.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.black))
                holder.itemView.setBackgroundResource(android.R.color.transparent)
            }

            holder.itemView.setOnClickListener {
                onItemClick?.invoke(position + 1) // Adjusted to match 1-based day
            }
        } else {
            holder.dayText.text = ""
        }
    }

    fun setSelectedDate(day: Int) {
        selectedDate = day
        notifyDataSetChanged() // Notify adapter that data set changed to update UI
    }

    fun updateData(newDays: List<String>) {
        daysOfMonth = newDays
        notifyDataSetChanged()
    }
}
