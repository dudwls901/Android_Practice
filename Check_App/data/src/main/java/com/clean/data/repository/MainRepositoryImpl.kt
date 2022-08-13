package com.clean.data.repository

import com.clean.data.mapper.MainMapper
import com.clean.data.repository.remote.datasource.MainDataSource
import com.clean.domain.model.DomainLoveResponse
import com.clean.domain.model.DomainScore
import com.clean.domain.repository.MainRepository
import com.clean.domain.utils.RemoteErrorEmitter
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun checkLoveCalculator(
        remoteErrorEmitter: RemoteErrorEmitter,
        host: String,
        key: String,
        mName: String,
        wName: String
    ): DomainLoveResponse? {
        //mapper를 이용해 data 계층의 response를 domain 계층의 response로 변환해 주자
        return MainMapper.loveMapper(mainDataSource.checkLoveCalculator(remoteErrorEmitter, host, key, mName, wName))
    }

    override fun getStatistics(): Task<DataSnapshot> {
        return mainDataSource.getStatistics()
    }

    override fun setStatistics(plusValue: Int): Task<Void> {
        return mainDataSource.setStatistics(plusValue)
    }

    override fun getScore(): Task<QuerySnapshot> {
        return mainDataSource.getScore()
    }

    override fun setScore(score: DomainScore): Task<Void> {
        return mainDataSource.setScore(MainMapper.scoreMapper(score))
    }
}