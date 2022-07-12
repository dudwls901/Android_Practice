package com.clean.data.repository.remote.datasource

import com.clean.data.remote.model.DataLoveResponse
import com.clean.domain.utils.RemoteErrorEmitter

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
}