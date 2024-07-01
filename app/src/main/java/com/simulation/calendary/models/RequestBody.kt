package com.simulation.calendary.models

data class RequestBody(
    val user_id: Int,
    val task_id: Int? = null,
    val task: TaskDetail? = null
)
