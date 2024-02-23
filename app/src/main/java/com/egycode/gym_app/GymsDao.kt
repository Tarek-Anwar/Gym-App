package com.egycode.gym_app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAllGyms() : List<Gym>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllGyms(gyms : List<Gym>)

    @Update(entity = Gym::class)
    suspend fun setFavouriteGym(gymsFavouriteState: GymsFavouriteState)
    @Query("SELECT * FROM gyms WHERE is_favourite = 1")
    suspend fun getFavouriteGyms(): List<Gym>

    @Update(entity = Gym::class)
    suspend fun updateAllFavourite(gyms: List<GymsFavouriteState>)
    @Query("SELECT * FROM gyms WHERE gym_id= :id")
    suspend fun getGymSelected(id: Int): Gym
}