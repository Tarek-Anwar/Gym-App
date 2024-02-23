package com.egycode.gym_app

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GymsFavouriteState(

    @ColumnInfo("gym_id")
    val id : Int,

    @ColumnInfo("is_favourite")
    val isFavourite : Boolean = false
    )
