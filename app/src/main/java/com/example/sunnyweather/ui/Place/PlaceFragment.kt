package com.example.sunnyweather.ui.Place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweather.MainActivity
import com.example.sunnyweather.R
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.ui.Weather.WeatherActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment:Fragment(){
    val viewmodel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter:PlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewmodel.isPlaceSaved()) {
            val place = viewmodel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()

            return
        }
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager=layoutManager
        adapter= PlaceAdapter(this,viewmodel.placeList)
        recyclerView.adapter=adapter
        searchPlaceEdit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s!=null){
                    viewmodel.searchPlace(s.toString())
                }
                else{
                    recyclerView.visibility=View.GONE
                    bgImageView.visibility=View.VISIBLE
                    viewmodel.placeList.clear()
                    adapter.notifyDataSetChanged()
                }

            }


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        viewmodel.PlaceLiveDate.observe(viewLifecycleOwner, Observer {result->
            val places=result.getOrNull()
            if (places!=null){
                recyclerView.visibility=View.VISIBLE
                bgImageView.visibility=View.GONE
                viewmodel.placeList.clear()
                viewmodel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
                val manager = SunnyWeatherApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(actionBarLayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            }
            else{
                Snackbar.make(actionBarLayout, "未查询到地点", Snackbar.LENGTH_LONG)
                    .setAction("关闭") {
                    }
                    .show()
                result.exceptionOrNull()?.printStackTrace()
            }

        })

    }




}