package fastcampus.part3.fastcampus_bookreview.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fastcampus.part3.fastcampus_bookreview.model.History

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM history")
    fun getAll() : List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    fun delete(keyword: String)
}