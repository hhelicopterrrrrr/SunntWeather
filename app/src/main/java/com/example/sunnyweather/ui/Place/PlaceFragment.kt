package com.example.sunnyweather.ui.Place

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweather.R
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
            }
            else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }

        })
    }




}