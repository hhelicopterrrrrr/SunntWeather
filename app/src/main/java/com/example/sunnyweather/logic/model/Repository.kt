package com.example.sunnyweather.logic.model

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun searchPlace(query:String)= liveData(Dispatchers.IO){
        val result=try {
            val placeResponse=SunnyWeatherNetwork.searchPlace(query)
            if (placeResponse.status=="ok"){
                val places=placeResponse.places
                Result.success(places)
            }
            else{
                Result.failure(RuntimeException("status不对"))
            }
        }
        catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)


    }
}