package com.example.memoapp_room_practice

import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity : 테이블
@Entity(tableName = "memo")
data class MemoEntity(
    //id를 기본키로 하고 기본적으로 1,2,3,4,5 들어감
    @PrimaryKey(autoGenerate = true)
    var id : Long?, var memo : String){

}
