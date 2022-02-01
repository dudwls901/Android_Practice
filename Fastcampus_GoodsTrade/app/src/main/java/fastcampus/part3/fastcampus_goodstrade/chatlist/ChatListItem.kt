package fastcampus.part3.fastcampus_goodstrade.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long
){
    //db에서 ChatList객체 통으로 받아오기 위한 빈 생성자
    constructor(): this("","","", 0)
}
