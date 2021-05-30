package com.algebra.soccernewtry.team.random.randompicking

import androidx.recyclerview.widget.RecyclerView

class ScrollSynchronizer {
    var boundRecyclerViews: ArrayList<RecyclerView> = ArrayList()
    var verticalOffset = 0

    private val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            // scroll every other rv to the same position
            boundRecyclerViews.filter { it != recyclerView }.forEach { targetView ->
                targetView.removeOnScrollListener(this)
                targetView.scrollBy(dx, dy)
                targetView.addOnScrollListener(this)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                verticalOffset = recyclerView.computeVerticalScrollOffset()
            }
        }
    }

    fun add(view: RecyclerView) {
        with (view) {
            if (boundRecyclerViews.contains(view)) {
                removeOnScrollListener(scrollListener)
                boundRecyclerViews.remove(this)
            }
            addOnScrollListener(scrollListener)
            boundRecyclerViews.add(this)
        }
    }


    fun addAll(recyclerViews: List<RecyclerView>) = recyclerViews.forEach { add(it) }
}