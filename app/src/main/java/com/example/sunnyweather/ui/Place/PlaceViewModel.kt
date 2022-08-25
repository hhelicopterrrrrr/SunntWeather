package com.example.sunnyweather.ui.Place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.Repository

class PlaceViewModel:ViewModel() {
    private val searchLiveDate = MutableLiveData<String>()
    val placeList=ArrayList<Place>()
    fun searchPlace(query:String){
        searchLiveDate.value=query
    }
    val PlaceLiveDate = Transformations.switchMap(searchLiveDate){query -> Repository.searchPlace(query)}

}