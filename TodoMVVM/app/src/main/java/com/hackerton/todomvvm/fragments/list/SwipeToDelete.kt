package com.hackerton.todomvvm.fragments.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

//ItemTouchHelper클래스는 onMove, onSwiped 필수적으로 오버라이딩 해야 함
//따라서 사용하지 않는 onMove는 여기서 Override해 놓고
//사용할 swiped는 실제 사용하는 곳에서 오버라이드
abstract class SwipeToDelete: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}