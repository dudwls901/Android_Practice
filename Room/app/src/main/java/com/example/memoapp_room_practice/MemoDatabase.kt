package com.example.memoapp_room_practice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//@Database(entities = arrayOf(MemoEntity::class), version = 1)
@Database(entities = [MemoEntity::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDAO() : MemoDAO

    //db 만드는 작업은 리소스를 많이 잡아먹는 일이기 때문에 앱 전체 프로세스 안에서 딱 한 번만 객체 생성하는 것이 유리
    // -> 싱글톤 패턴
    companion object {
        var INSTANCE : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase?{
            if(INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db")
                            //db를 한 번 생성하고나서 스키마에 대한 수정이 있을 때 db 버전을 올려줘야 함(Long -> Int로 수정한다든지)
                            //fallbacktodestruct는 이전 버전 db를 모두 드랍하고 새로운 데이터로 시작(이전 버전의 모든 데이터 날라감)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}