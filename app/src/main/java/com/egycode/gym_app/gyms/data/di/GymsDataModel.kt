package com.egycode.gym_app.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.egycode.gym_app.gyms.data.local.GymsDataBase
import com.egycode.gym_app.gyms.data.remote.GymApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModel {
    @Provides
    fun provideGymApiService(retrofit: Retrofit) : GymApiService = retrofit.create(GymApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gyms-fe7ee-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGymsDao(gymsDataBase: GymsDataBase) = gymsDataBase.dao

    @Singleton
    @Provides
    fun provideRoomDataBase(
        @ApplicationContext context: Context
    ): GymsDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            GymsDataBase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration()
            .build()

    }
}