package com.simulation.calendary.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simulation.calendary.models.Task
import com.simulation.calendary.models.TaskDetail
import com.simulation.calendary.models.TaskResponse
import com.simulation.calendary.repositories.TaskRepository
import com.simulation.calendary.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TaskViewModel(val repository: TaskRepository): ViewModel() {

    private val _taskList: MutableLiveData<Resource<TaskResponse>> = MutableLiveData()
    val taskList: LiveData<Resource<TaskResponse>>
            get() = _taskList
    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> get() = _showToast

    init{
        getTaskList()
    }

    private fun getTaskList(){
        viewModelScope.launch {
            _taskList.postValue(Resource.Loading())
            val response = repository.getTaskList()
            _taskList.postValue(handleTaskResponse(response))
        }
    }

    private fun handleTaskResponse(response: Response<TaskResponse>): Resource<TaskResponse>{
        if(response.isSuccessful){
            response.body().let {
                if(it != null){
                    return Resource.Success(it)
                }
                else {
                    return Resource.Error(message = "Add some tasks from the calendar!")
                }
            }
        }else{
            return Resource.Error(message = response.message())
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            _taskList.postValue(Resource.Loading())
            val response = repository.deleteTask(task)
            if (response.isSuccessful) {
                getTaskList()
            } else {
                _showToast.postValue("Failed to delete task")
            }
        }
    }

}