package com.simulation.calendary.repositories

import com.simulation.calendary.apis.RetrofitHelper
import com.simulation.calendary.models.RequestBody
import com.simulation.calendary.models.Task
import com.simulation.calendary.models.TaskDetail
import com.simulation.calendary.models.TaskResponse
import com.simulation.calendary.utils.Constants
import retrofit2.Response

class TaskRepository {

    suspend fun getTaskList() : Response<TaskResponse>{
        val response = RetrofitHelper.taskApi.getCalendarTaskLists(RequestBody(Constants.USER_ID))
        return cleanUpTaskList(response)
    }

    suspend fun deleteTask(task: Task): Response<TaskResponse> {
        return RetrofitHelper.taskApi.deleteTask(RequestBody(Constants.USER_ID, task.taskId))
    }

    private fun cleanUpTaskList(response: Response<TaskResponse>): Response<TaskResponse>{
        return if (response.isSuccessful) {
            val cleanedTasks = response.body()?.tasks?.filter { task ->
                !(task.taskDetail.description == null && task.taskDetail.title == null)
            }
            val cleanedResponse = response.body()?.copy(tasks = cleanedTasks ?: emptyList())
            Response.success(cleanedResponse)
        } else {
            response
        }
    }
}