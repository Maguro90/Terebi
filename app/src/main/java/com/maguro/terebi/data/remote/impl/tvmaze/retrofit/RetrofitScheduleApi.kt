package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.data.remote.ScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.responses.ScheduleItemResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class RetrofitScheduleApi(
    retrofit: Retrofit
) : ScheduleApi {

    private val retrofitApi = retrofit.create(RetrofitApi::class.java)

    override suspend fun getSchedule(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<List<ScheduleItem>> {

        return wrapResponse {
            retrofitApi.getSchedule(
                date = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                countryCode = countryCode
            ).map {
                it.getScheduleItemModel()
            }
        }

    }

    private interface RetrofitApi{

        @GET("schedule")
        suspend fun getSchedule(
            @Query("date") date: String,
            @Query("country") countryCode: String
        ): List<ScheduleItemResponse>

    }
}