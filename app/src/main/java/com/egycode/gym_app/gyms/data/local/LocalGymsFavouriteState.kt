package com.egycode.gym_app.gyms.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class LocalGymsFavouriteState(

    @ColumnInfo("gym_id")
    val id : Int,

    @ColumnInfo("is_favourite")
    val isFavourite : Boolean = false
    )
