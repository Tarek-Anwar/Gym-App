package com.egycode.gym_app.gyms.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GymApiService {

    @GET("gyms.json")
    suspend fun getGyms() : List<RemoteGym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGymDetails(
       @Query("equalTo") id: Int
    ) : Map<String, RemoteGym>
}