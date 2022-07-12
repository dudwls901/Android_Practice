package com.clean.data.mapper

import com.clean.data.remote.model.DataLoveResponse
import com.clean.domain.model.DomainLoveResponse

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
}