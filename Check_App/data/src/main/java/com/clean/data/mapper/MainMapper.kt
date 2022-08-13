package com.clean.data.mapper

import com.clean.data.remote.model.DataLoveResponse
import com.clean.data.remote.model.DataScore
import com.clean.domain.model.DomainLoveResponse
import com.clean.domain.model.DomainScore

object MainMapper {

    fun loveMapper(
        dataResponse: DataLoveResponse?
    ) : DomainLoveResponse?{
        return if (dataResponse != null){
            DomainLoveResponse(
                fName = dataResponse.fName,
                sName = dataResponse.sName,
                percentage = dataResponse.percentage,
                result = dataResponse.result
            )
        } else dataResponse
    }

    fun scoreMapper(
        domainResponse: DomainScore
    ) : DataScore{
        return DataScore(
            man = domainResponse.man,
            woman = domainResponse.woman,
            percentage = domainResponse.percentage,
            data = domainResponse.data
        )
    }
}