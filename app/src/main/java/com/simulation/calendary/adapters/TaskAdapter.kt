package com.simulation.calendary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simulation.calendary.R
import com.simulation.calendary.models.Task
import com.simulation.calendary.utils.TaskDiffUtil

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onDeleteClick(position: Int)
    }

    private var taskList = emptyList<Task>()
    class TaskViewHolder(val itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.tvTaskDescription)
        val btnDeleteTask: Button = itemView.findViewById(R.id.btnDelete)

        init{
            btnDeleteTask.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.task_layout,parent,false),mListener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.apply {
            taskTitle.text = task.taskDetail.title
            taskDescription.text = task.taskDetail.description

        }
    }

    fun setData(newTaskList: List<Task>){
        val diffUtil = TaskDiffUtil(taskList,newTaskList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        taskList = newTaskList
        diffResults.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun getItemAt(position: Int): Task {
        return taskList[position]
    }
}