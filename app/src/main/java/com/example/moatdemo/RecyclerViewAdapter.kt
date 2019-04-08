package com.example.moatdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.item.view.*
import java.lang.IllegalArgumentException

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(),StickyHeaderAdapter {

    private var dataList:MutableList<Item> = mutableListOf(Item("Station 1",1)
        ,Item("Station 2",1),
        Item("Station 3",1),
        Item("Station 4",1),
        Item("Station 5",1),
        Item("Header 1",2),
        Item("Station 6",1),
        Item("Station 7",1),
        Item("Station 8",1),
        Item("Station 9",1),
        Item("Header 2",2),
        Item("Station 10",1),
        Item("Station 11",1),
        Item("Station 12",1),
        Item("Station 13",1),
        Item("Station 14",1),
        Item("Station 15",1),
        Item("Station 16",1),
        Item("Header 3",2),
        Item("Station 17",1),
        Item("Station 18",1),
        Item("Station 19",1),
        Item("Station 20",1),
        Item("Station 21",1),
        Item("Station 22",1),
        Item("Header 4",2),
        Item("Station 23",1),
        Item("Station 24",1),
        Item("Station 25",1),
        Item("Station 26",1),
        Item("Station 27",1),
        Item("Station 28",1),
        Item("Station 29",1),
        Item("Station 30",1),
        Item("Station 31",1))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                StationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
            }
            2->{
                HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header,parent,false))
            }
            else->{
                throw IllegalArgumentException()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].itemType
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is StationViewHolder){
            holder.bind(dataList[position].data)
        }else if (holder is HeaderViewHolder ){
            holder.bind(dataList[getHeaderPosition(position)].data)
        }
    }

    override fun getHeaderPosition(adapterPosition: Int): Int {
        var i = adapterPosition
        while (i >= 0 && dataList.size > 0 && i <= dataList.size - 1) {
            val item = dataList[i]
            if (item.itemType == 2) {
                return i
            }
            i--
        }

        return RecyclerView.NO_POSITION
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header,parent,false))
    }

    override fun onBindHeaderViewHolder(vh: RecyclerView.ViewHolder, position: Int) {

         if(vh is HeaderViewHolder){
             vh.bind(dataList[getHeaderPosition(position)].data)
         }
    }

    class StationViewHolder(view:View): RecyclerView.ViewHolder(view) {
        fun bind(data: String){
            itemView.itemText.text = data
        }
    }

    class HeaderViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(data: String){
            itemView.headerText.text = data
        }
    }

    data class Item(var data:String,var itemType:Int)
}