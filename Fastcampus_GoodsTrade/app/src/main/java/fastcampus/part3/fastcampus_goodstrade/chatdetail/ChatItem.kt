package fastcampus.part3.fastcampus_goodstrade.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String
){

    constructor(): this("","")
}
