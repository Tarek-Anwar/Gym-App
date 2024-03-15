package com.egycode.gym_app.gyms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAllGyms(): List<LocalGym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllGyms(gyms: List<LocalGym>)

    @Update(entity = LocalGym::class)
    suspend fun setFavouriteGym(gymsFavouriteState: LocalGymsFavouriteState)

    @Query("SELECT * FROM gyms WHERE is_favourite = 1")
    suspend fun getFavouriteGyms(): List<LocalGym>

    @Update(entity = LocalGym::class)
    suspend fun updateAllFavourite(gyms: List<LocalGymsFavouriteState>)

    @Query("SELECT * FROM gyms WHERE gym_id= :id")
    suspend fun getGymSelected(id: Int): LocalGym
}