package com.simulation.calendary.repositories

import com.simulation.calendary.apis.RetrofitHelper
import com.simulation.calendary.models.RequestBody
import com.simulation.calendary.models.TaskDetail
import com.simulation.calendary.models.TaskResponse
import com.simulation.calendary.utils.Constants
import retrofit2.Response

class CalendarRepository {

    suspend fun storeTask(task: TaskDetail): Response<TaskResponse> {
        return RetrofitHelper.taskApi.storeCalendarTask(RequestBody(Constants.USER_ID,null,task))
    }

}