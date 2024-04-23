package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.remote.impl.tvmaze.responses.ScheduleItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitScheduleApi {
    @GET("schedule")
    suspend fun getSchedule(
        @Query("date") date: String,
        @Query("country") countryCode: String
    ): List<ScheduleItemResponse>

}