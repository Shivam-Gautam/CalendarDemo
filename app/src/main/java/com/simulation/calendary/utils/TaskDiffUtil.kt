package com.simulation.calendary.utils

import androidx.recyclerview.widget.DiffUtil
import com.simulation.calendary.models.Task

class TaskDiffUtil(
    private val oldList: List<Task>,
    private val newList: List<Task>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].taskId == newList[newItemPosition].taskId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].taskId != newList[newItemPosition].taskId -> false
            oldList[oldItemPosition].taskDetail.description == newList[newItemPosition].taskDetail.description -> false
            oldList[oldItemPosition].taskDetail.title == newList[newItemPosition].taskDetail.title -> false
            else -> true
        }
    }
}