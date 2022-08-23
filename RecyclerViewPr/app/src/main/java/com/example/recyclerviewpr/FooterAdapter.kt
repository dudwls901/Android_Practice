package com.example.recyclerviewpr

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpr.databinding.ItemFooterBinding
import com.example.recyclerviewpr.databinding.ItemHeaderBinding
import com.example.recyclerviewpr.databinding.ItemProfileBinding

class FooterAdapter : RecyclerView.Adapter<MainViewHolders.FooterViewHolder>() {

    private var footerText = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolders.FooterViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return MainViewHolders.FooterViewHolder(
            ItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolders.FooterViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        holder.bind(footerText)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ")
        return 1
    }

    fun setData(data: String) {
        Log.d(TAG, "setData: ")
        footerText = data
    }

    companion object {
        @JvmStatic
        val TAG = FooterAdapter::class.java.simpleName
    }
}