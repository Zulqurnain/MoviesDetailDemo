package com.jutt.moviesdetaildemo.view.decorations

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    recyclerView: RecyclerView? = null,
    layoutManager: RecyclerView.LayoutManager? = recyclerView?.layoutManager,
    @Px space: Int = 0,
    val spaceBorderMultiplier: Int = 2
) : RecyclerView.ItemDecoration() {

    private val spaceHorizontal: Int
    private val spaceVertical: Int

    init {
        if ((layoutManager as? LinearLayoutManager)?.orientation == LinearLayoutManager.HORIZONTAL) {
            spaceHorizontal = space
            spaceVertical = 0
        } else {
            spaceHorizontal = 0
            spaceVertical = space
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition: Int = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        when {
            (itemPosition == RecyclerView.NO_POSITION) -> return
            (itemCount > 0 && (itemCount == 1)) -> {
                outRect.left = spaceHorizontal.times(spaceBorderMultiplier)
                outRect.top = spaceVertical.times(spaceBorderMultiplier)
                outRect.right = spaceHorizontal.times(spaceBorderMultiplier)
                outRect.bottom = spaceVertical.times(spaceBorderMultiplier)
            }
            (itemPosition == 0) -> {
                outRect.left = spaceHorizontal.times(spaceBorderMultiplier)
                outRect.top = spaceVertical.times(spaceBorderMultiplier)
                outRect.right = spaceHorizontal
                outRect.bottom = spaceVertical
            }
            (itemCount > 0 && (itemPosition == itemCount - 1)) -> {
                outRect.left = spaceHorizontal
                outRect.top = spaceVertical
                outRect.right = spaceHorizontal.times(spaceBorderMultiplier)
                outRect.bottom = spaceVertical.times(spaceBorderMultiplier)
            }
            else -> {
                outRect.left = spaceHorizontal
                outRect.top = spaceVertical
                outRect.right = spaceHorizontal
                outRect.bottom = spaceVertical
            }
        }
    }
}