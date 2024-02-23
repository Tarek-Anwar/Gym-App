package com.egycode.gym_app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Gym::class],
    version = 2,
    exportSchema = false
)
abstract class GymsDataBase : RoomDatabase() {

    abstract val dao : GymsDao

    companion object{

        private var daoInstance : GymsDao? = null
        private fun buildDatabase(context : Context) : GymsDataBase{
            return Room.databaseBuilder(
                context.applicationContext,
                GymsDataBase::class.java,
                "gyms_database"
            ).fallbackToDestructiveMigration()
                .build()
        }
        fun getDaoInstance(context: Context) : GymsDao{
            synchronized(this){
                if (daoInstance == null) daoInstance = buildDatabase(context).dao
                return daoInstance as GymsDao
            }
        }
    }
}