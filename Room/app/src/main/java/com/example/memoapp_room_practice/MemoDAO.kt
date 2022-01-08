package com.example.memoapp_room_practice

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MemoDAO {
    //insert할 때 pk가 겹치면 덮어쓰기
    @Insert(onConflict = REPLACE)
    fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>

    @Delete
    fun delete(memo : MemoEntity)
}