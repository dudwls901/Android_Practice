package com.example.retrofit2practice.Utils

//문자열이 제이슨 형태인지
//축약형 3가지 방식
fun String?.isJsonObject():Boolean {
//1.
    if(this?.startsWith("{")==true && this.endsWith("}")){
        return true
    }
    else{
        return false
    }
    //2.
//    return this?.startsWith("{")==true && this.endsWith("}")

}
//3.
//fun String?.isJsonObject():Boolean = this?.startsWith("{")==true && this.endsWith("}")

//문자열이 제이슨 배열인지
fun String?.isJsonArray() : Boolean{
    if(this?.startsWith("[")==true && this.endsWith("]")){
        return true
    }
    else{
        return false
    }
}