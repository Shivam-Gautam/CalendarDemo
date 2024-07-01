package com.simulation.calendary.apis

import com.simulation.calendary.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper private constructor(){

    companion object{

        @Volatile
        private var instance : Retrofit? = null

        private fun getInstance() = synchronized(this){
            if(instance == null){
                instance = Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(
                    GsonConverterFactory.create()).build()
            }
            instance
        }

        val taskApi: TaskApiInterface by lazy{
            getInstance()!!.create(TaskApiInterface::class.java)
        }
    }

}