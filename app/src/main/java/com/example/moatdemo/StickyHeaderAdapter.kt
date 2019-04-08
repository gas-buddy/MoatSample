package com.example.moatdemo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface StickyHeaderAdapter {
    fun getHeaderPosition(adapterPosition: Int): Int
    fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindHeaderViewHolder(vh: RecyclerView.ViewHolder, position: Int)
}