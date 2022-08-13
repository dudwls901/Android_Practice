package com.clean.data.remote.model

data class DataScore (
    //남자 이름
    val man: String,
    //여자 이름
    val woman: String,
    //확률
    val percentage: Int,
    //시간
    val data: String
){
    constructor() : this("오류","오류",0,"오류")
}