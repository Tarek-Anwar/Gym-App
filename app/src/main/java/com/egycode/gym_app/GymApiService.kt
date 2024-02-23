package com.egycode.exercise

import retrofit2.http.GET
import retrofit2.http.Query

interface GymApiService {

    @GET("gyms.json")
    suspend fun getGyms() : List<GymModel>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGymDetails(
       @Query("equalTo") id: Int
    ) : Map<String,GymModel>
}