package com.example.memoapp_room_practice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context,
                var list: List<MemoEntity>,
                var onDeleteListener: OnDeleteListener
                ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //row_memo의 틀을 만듦
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView : View = LayoutInflater.from(context).inflate(R.layout.row_memo, parent, false)

        // -> onBindViewHolder
        return MyViewHolder(itemView)
    }

    //row_memo에 내용 넣기
    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        //각각의 포지션에 맞는 메모 삽입
        val memo : MemoEntity = list[position]
        holder.memo.text = memo.memo

        // row 쭉 누르면 삭제 구현
        holder.root.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                onDeleteListener.onDeleteListener(memo)
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val memo = itemView.findViewById<TextView>(R.id.memo_tv)
        val root = itemView.findViewById<ConstraintLayout>(R.id.root)
    }


}