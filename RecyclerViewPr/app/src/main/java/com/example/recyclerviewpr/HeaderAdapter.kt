package com.example.recyclerviewpr

import android.annotation.SuppressLint
import android.text.method.Touch
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpr.databinding.ItemHeaderBinding
import com.example.recyclerviewpr.databinding.ItemProfileBinding

class HeaderAdapter : RecyclerView.Adapter<MainViewHolders.HeaderViewHolder>() {

    private var headerText = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolders.HeaderViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return MainViewHolders.HeaderViewHolder(
            ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolders.HeaderViewHolder, position: Int) {
        Log.d( TAG,"onBindViewHolder: ")
        holder.bind(headerText)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ")
        return 1
    }

    fun setData(data: String){
        Log.d(TAG, "setData: ")
        headerText = data
    }

    companion object {
        @JvmStatic
        val TAG = HeaderAdapter::class.java.simpleName
    }
}