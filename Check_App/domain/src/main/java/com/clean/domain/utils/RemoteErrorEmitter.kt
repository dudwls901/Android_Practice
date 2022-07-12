package com.clean.domain.utils

interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}