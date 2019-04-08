package com.example.moatdemo

import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderListDecorator(val adapter: StickyHeaderAdapter?): RecyclerView.ItemDecoration() {

    private var header:View?=null
    private var child:View?=null
    private var forListItemTypes:Set<Int> = setOf(1,2)


    override fun onDrawOver(canvas: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        child = recyclerView.getChildAt(0)
        child?.run {
            val adapterPos = recyclerView.getChildLayoutPosition(this)
            if (adapterPos != RecyclerView.NO_POSITION &&
                hasHeader(adapter, adapterPos) &&
                canDrawHeaderOnItemType(recyclerView.adapter!!.getItemViewType(adapterPos))
            )
            {
                header = getHeaderView(recyclerView, adapterPos)

                header?.run {
                    canvas.save()
                    val left = recyclerView.left
                    val top = getHeaderTop(adapter, recyclerView, child, this.getHeight(), adapterPos)
                    canvas.translate(left.toFloat(), top.toFloat())
                    header?.translationX = left.toFloat()
                    header?.translationY = top.toFloat()
                    header?.draw(canvas)
                    canvas.restore()
                }
            }
        }

    }

    private fun getHeaderView(recyclerView: RecyclerView, adapterPosition: Int): View? {
        val vh = adapter?.onCreateHeaderViewHolder(recyclerView)

        vh?.run {
            adapter?.onBindHeaderViewHolder(vh, adapterPosition)


            val header = vh.itemView

            val widthSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY)
            val heightSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.height, View.MeasureSpec.UNSPECIFIED)

            val headerWidth = ViewGroup.getChildMeasureSpec(
                widthSpec,
                recyclerView.paddingLeft + recyclerView.paddingRight,
                header.layoutParams.width
            )
            val headerHeight = ViewGroup.getChildMeasureSpec(
                heightSpec,
                recyclerView.paddingTop + recyclerView.paddingBottom,
                header.layoutParams.height
            )
            header.measure(headerWidth, headerHeight)
            header.layout(0, 0, header.measuredWidth, header.measuredHeight)

            return header
        }

        return null

    }

    protected fun hasHeader(adapter: StickyHeaderAdapter?, position: Int): Boolean {

        val headerPosition = getHeaderPosition(adapter, position)
        return headerPosition >= 0
    }

    protected fun canDrawHeaderOnItemType(itemType: Int): Boolean {
        return itemType >= 0 && (forListItemTypes.contains(itemType))
    }


    protected fun getHeaderTop(
        adapter: StickyHeaderAdapter?,
        recyclerView: RecyclerView,
        child: View?,
        headerHeight: Int,
        adapterPos: Int
    ): Int {
        var view:View? = null
        val top = child?.y?.toInt()?:0 - headerHeight

        val headerPosition = getHeaderPosition(adapter, adapterPos)

        val count = recyclerView.childCount
        for (i in 1 until count) {
            view = recyclerView.getChildAt(i)
            val adapterPosHere = recyclerView.getChildLayoutPosition(view)
            if (adapterPosHere != RecyclerView.NO_POSITION) {

                val nextHeaderPosition = getHeaderPosition(adapter, adapterPosHere)
                if (nextHeaderPosition != headerPosition) {

                    val offset = view.getY().toInt() - headerHeight
                    return if (offset < 0) {
                        offset
                    } else {
                        break
                    }
                }
            }
        }

        return Math.max(0, top)
    }

    protected fun getHeaderPosition(adapter: StickyHeaderAdapter?, adapterPos: Int): Int {
        return adapter?.getHeaderPosition(adapterPos)?:0
    }
}