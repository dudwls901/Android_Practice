package fastcampus.chapter1.fastcampus_recorder

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton

/*
Context와 AttributeSet을 파라미터로 전달해야 레이아웃에디터(인드로이드스튜디오의 레이아웃 편집기)에서
RecordButton을 추가하고 속성을 수정할 수 있음
 */
//AppCompat : 이전 버전의 안드로이드에 대한 호환성 기존 클래스를 래핑해서 이전 버전에서도 새로 출시한 기능들을 정상적으로 동작하게 도와주는 라이브러리
//xml에선 그냥 TextView로 써도 xml 내부에 정의된 텍스트뷰를 자동으로 AppCompat으로 매핑할 수 있는 클래스가 있는 경우 자동으로 매핑해줌
//코드에선 이러한 기능이 없기 때문에 직접 AppCompat을 붙인다.
//최근에는 AppCompat을 Material 디자인 가이드에 맞춰서 클래스를 매핑시켜 전환시켜주는 기능도 제공하는데 현재 이미지버튼은 해당 안 됨
class RecordButton(context: Context, attrs: AttributeSet) : AppCompatImageButton(context, attrs) {

    //xml에서 하는 것보다 재사용성bb
    init{
        setBackgroundResource(R.drawable.shape_oval_button)
    }

    //State에 따라 레코드 버튼 변경
    fun updateIconWithState(state : State){
        when(state){
            State.BEFORE_RECORDING ->{
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECORDING -> {
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECORDING -> {
                setImageResource(R.drawable.ic_play)
            }
            State.ON_PLAYING -> {
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}