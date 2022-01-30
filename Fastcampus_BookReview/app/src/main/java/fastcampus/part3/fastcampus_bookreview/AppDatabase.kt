package fastcampus.part3.fastcampus_bookreview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fastcampus.part3.fastcampus_bookreview.dao.HistoryDAO
import fastcampus.part3.fastcampus_bookreview.dao.ReviewDAO
import fastcampus.part3.fastcampus_bookreview.model.History
import fastcampus.part3.fastcampus_bookreview.model.Review

@Database(entities = [History::class, Review::class], version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDAO(): HistoryDAO
    abstract fun reviewDAO(): ReviewDAO



    companion object{
        private var INSTANCE : AppDatabase? =null
        private val migration_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `REVIEW` (`id` INTEGER, `review` TEXT," + "PRIMARY KEY(`id`))")
            }
        }
        fun getInstance(context : Context) : AppDatabase?{
            if(INSTANCE ==null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "BookSearchDB"
                    )
                        .addMigrations(migration_1_2)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}