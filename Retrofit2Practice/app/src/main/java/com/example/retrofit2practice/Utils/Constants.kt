package com.example.retrofit2practice.Utils

object Constants {
    const val TAG : String = "로그"
}

enum class SEARCH_TYPE{
    PHOTO,
    USER
}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}

object API{
    const val BASE_URL : String ="https://api.unsplash.com/"

    const val CLIENT_ID : String = "gu6iUOgP37gv9pbeaRHocTG-S1AyyBUDoN67vNavtLU"

    const val SEARCH_PHOTO : String ="search/photos"
    const val SEARCH_USERS : String ="search/users"
}