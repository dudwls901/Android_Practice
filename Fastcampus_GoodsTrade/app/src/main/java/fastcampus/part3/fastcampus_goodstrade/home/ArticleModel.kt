package fastcampus.part3.fastcampus_goodstrade.home

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createdAt: Long,
    val price: String,
    val imageUrl: String
){
    //Firebase RealTime db에 모델 클래스를 그대로 주고받기 위해선 빈 생성자 필요
    constructor() : this("","",0,"","")
}
