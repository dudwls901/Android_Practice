package fastcampus.part3.fastcampus_todaysentence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import fastcampus.part3.fastcampus_todaysentence.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initData()
    }

    private fun initViews() {
        //뷰 페이저 스와이프 효과 주기
        binding.viewPager.setPageTransformer { page, position ->
            when{
                //현재 화면인 0과 좌 -1 우 1만 신경쓰면 됨 나머지 값은 알파 0 줘버리기
                position.absoluteValue >= 1f ->{
                    //좌 우 화면 알파값 0으로
                    page.alpha = 0f
                }
                position ==0f ->{
                    //중앙 화면 알파값 1
                    page.alpha - 1f
                }
                else ->{
                    //중앙 화면 + 바로 좌우 화면 외에 나머지
                    //좌우측 화면들이 중앙기준으로 1 -> 0.5 -> 0 이런식으로 점차적으로 투명해짐
                    //현재 화면이 좌우로 완전히 넘어갈 때 알파가 0이 되면 효과가 두드러지지 않음
                    //고로 반절 넘어갔을 때 알파를 0으로 만들기
                    page.alpha = 1f - 2 * position.absoluteValue
                }
            }
        }
    }

    private fun initData() {
        //디폴트로 설정된 가져오기 간격 12시간을 단축시키기
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                //앱 들어올 때마다 fetch 진행
                minimumFetchIntervalInSeconds = 0
            }
        )
        //리모트 컨피그에 굉장한 의존성을 갖고 있기 때문에 이를 못가져왔을 때 기본 값을 보여줄 스펙 필요
        // ->실무에선 setDefaultAyn, 이 프로젝트에선 안 만듦
        //fetch자체가 비동기로 이루어지기 때문에 리스너 등록
        //fetch가 성공했다기 보다는, fetch랑 activate란 task를 완료했다는 의미
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            //인디케이터
            binding.progressBar.visibility = View.GONE
            //성공한 경우
            if (it.isSuccessful) {
                //gson 말고 일단 기본 api 사용해서 json  파싱
                val quotes = parseQuotesJson(remoteConfig.getString("quotes"))
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")
                displayQuotesPager(quotes, isNameRevealed)

            } else {

            }
        }
    }

    private fun displayQuotesPager(quotes: List<Quote>, isNameReveled: Boolean){
        val adapter = QuotePagerAdpater(
            quotes,
            isNameReveled
        )

        binding.viewPager.adapter =adapter
        //현재 구현한 무한 스크롤의 사이즈는 Int.MAX_VALUE인데, 처음 보이는 화면은 인덱스 0 화면이다.
        //따라서 현재는 우측으로만 무한 스크롤인 상태이고, 이를 좌우 무한 스크롤로 만들기 위해
        //첫 화면을 리스트의 가운데 인덱스로 설정한다.
        // smoothScroll을 디폴트인 true로 하면 시작하자마자 리스트의 중앙인덱스로 부드럽게 넘어간다
        // 이렇게 앱을 시작하자마자 중앙 인덱스까지 스크롤되는 것이 보이면 자연스럽지 않기 때문에 명시적으로 smoothScroll을 false
        binding.viewPager.setCurrentItem((adapter.itemCount/2)-(adapter.itemCount/2%quotes.size),false)
    }


    //json 파싱
    private fun parseQuotesJson(json: String): List<Quote> {
        val jsonArray = JSONArray(json)//jsonobject의 리스트
        var jsonList = emptyList<JSONObject>()
        for (idx in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(idx)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }
        return jsonList.map {
            Quote(
                quote = it.getString("quote"),
                name = it.getString("name")
            )
        }
    }


}