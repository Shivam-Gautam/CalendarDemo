package com.simulation.calendary.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simulation.calendary.R
import com.simulation.calendary.adapters.TaskAdapter
import com.simulation.calendary.models.Task
import com.simulation.calendary.models.TaskDetail
import com.simulation.calendary.repositories.TaskRepository
import com.simulation.calendary.utils.Resource
import com.simulation.calendary.viewmodels.TaskViewModel
import com.simulation.calendary.viewmodels.TaskViewModelFactory

class TaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setupViews()
        setupObservers()
    }

    private fun initData(){
        val taskRepository = TaskRepository()
        val viewModelFactory = TaskViewModelFactory(taskRepository)
        taskViewModel = ViewModelProvider(this,viewModelFactory)[TaskViewModel::class.java]
        taskAdapter = TaskAdapter()
    }

    private fun setupViews(){
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rvTask)
        recyclerView?.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        taskAdapter.setOnItemClickListener(object : TaskAdapter.onItemClickListener{
            override fun onDeleteClick(position: Int) {
                taskViewModel.deleteTask(taskAdapter.getItemAt(position))
            }
        })
    }

    private fun setupObservers(){
        taskViewModel.taskList.observe(viewLifecycleOwner){response ->
            Log.d("response",response.data?.tasks.toString())
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    taskAdapter.setData(response.data?.tasks ?: emptyList())
                }
                is Resource.Error -> {
                    hideProgressBar()
                    setErrorMessage()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        taskViewModel.showToast.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun showProgressBar(){
        val progressBar : ProgressBar? = view?.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        val progressBar : ProgressBar? = view?.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE
    }

    private fun setErrorMessage(){

    }
}