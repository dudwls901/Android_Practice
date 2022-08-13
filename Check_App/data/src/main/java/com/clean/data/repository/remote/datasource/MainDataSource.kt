package com.clean.data.repository.remote.datasource

import com.clean.data.remote.model.DataLoveResponse
import com.clean.data.remote.model.DataScore
import com.clean.domain.utils.RemoteErrorEmitter
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.QuerySnapshot

//생성자로 에러 타입 등을 알려주는 인터페이스와 API 호출에 필요한 값 받기
interface MainDataSource {
    suspend fun checkLoveCalculator(
        remoteErrorEmitter: RemoteErrorEmitter,
        host: String,
        key: String,
        //fName = 남자 이름
        mName: String,
        //sName = 여자 이름
        wName: String
    ) : DataLoveResponse?

    //통계 가져오기
    fun getStatistics() : Task<DataSnapshot>

    //통계 저장하기
    fun setStatistics(plusValue: Int) : Task<Void>

    //전적 가져오기
    fun getScore() : Task<QuerySnapshot>

    //전적 저장하기
    fun setScore(score: DataScore) : Task<Void>
}