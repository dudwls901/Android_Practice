package fastcampus.chapter1.fastcampus_calculator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fastcampus.chapter1.fastcampus_calculator.dao.HistoryDao
import fastcampus.chapter1.fastcampus_calculator.model.History

//히스토리란 테이블을 사용하겠다고 등록
//디비 변경될 때 마이그레이션을 위해 버전 설정
@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDao() : HistoryDao

    //db 만드는 작업은 리소스를 많이 잡아먹는 일이기 때문에 앱 전체 프로세스 안에서 딱 한 번만 객체 생성하는 것이 유리
    // -> 싱글톤 패턴
    companion object{
        var INSTANCE : AppDatabase? = null

        fun getInstanc(context : Context) : AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "historyDB"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}