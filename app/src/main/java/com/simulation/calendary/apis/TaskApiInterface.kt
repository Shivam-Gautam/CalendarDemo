package com.simulation.calendary.apis

import com.simulation.calendary.models.RequestBody
import com.simulation.calendary.models.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApiInterface {

    @POST("api/storeCalendarTask")
    suspend fun storeCalendarTask(
        @Body requestBody: RequestBody
    ): Response<TaskResponse>

    @POST("api/getCalendarTaskList")
    suspend fun getCalendarTaskLists(
        @Body requestBody: RequestBody
    ): Response<TaskResponse>

    @POST("api/deleteCalendarTask")
    suspend fun deleteTask(
        @Body requestBody: RequestBody
    ) : Response<TaskResponse>
}