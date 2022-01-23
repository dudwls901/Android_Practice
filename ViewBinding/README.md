# View Binding
![image](https://user-images.githubusercontent.com/66052467/150661027-3bd64fd8-df45-41b8-b92b-7284817d8b97.png)

## findViewById -> Butter knife library or kotlin synthetics -> view binding

### findViewById의 문제
- 속도가 상대적으로 느리다.
- 타입을 잘못 적어서 cast exception이 발생할 위험이 있다.
- 실수로 유효하지 않은 id를 사용하면 null 오류가 발생할 수 있다.
- 뷰가 많으면 그 만큼 코드가 길어진다.
### kotlin synthetics의 문제
- 전역 네임 스페이스가 오염
- 리사이클러뷰의 뷰홀더에선 캐싱을 해주지 않는다.
- Kotlin만 지원 가능하다.

### view binding
- null 안전 : 뷰의 직접 참조를 생성하므로 유효하지 않은 뷰 id로 인해 null 포인터 예외가 발생할 위험이 없다.
  - 레이아웃의 일부 구성에만 뷰가 있는 경우 결합 클래스에서 참조를 포함하는 필드가 @Nullable로 표시된다.
- 유형 안전 : 각 바인딩 클래스에 있는 필드의 유형이 XML 파일에서 참조하는 뷰와 일치하기 때문에 
              클래스 변환 예외가 발생할 위험이 없다.
- 따라서 레이아웃과 코드 사이에 일치하지 않는 부분은 컴파일 오류가 발생하므로 앱 배포 후 발생할 수 있는 런타임 오류를 방지한다.
- Android Studio에서 xml 파일의 내용을 변경할 시, 해당 xml 파일에 대한 binding object만을 메모리에서 바로 갱신하도록 최적화 되어 있다.
  따라서 xml 파일 내용 변경 시 Android Studio에서 이 변경 사항을 바로 적용하기 위해 프로젝트 전체를 rebuild 할 필요가 없다.

#### 그래서 view binding이란?

뷰와 상호작용하는 코드를 쉽게 작성할 수 있도록 모듈에 있는 각 XML 레이아웃 파일의 결합 클래스를 생성한다.
바인딩 클래스와 인스턴스에는 상응하는 레이아웃에 ID가 있는 모든 뷰의 직접 참조가 포함된다.
즉, 이전 findViewById보다 간결한 코드로, null 안전과 유형 안전을 챙기고, 성능까지 챙길 수 있는 안드로이드 아키텍처 구성요소로서 
앱 모듈별로 설정하여 사용할 수 있는 기능이다.

### view binding 사용

#### 1.gradle 추가
<pre>
 <code>
 //Android Studio 4.0 이상
    android {
        ...
        viewBinding {
            enabled = true
        }
    }
    
 //Android Studio 3.6 ~ 4.0 
    android {
      ...
      viewBinding {
          enabled true
      }
}
 </code>
</pre>

#### 2. viewbind ignore
gradle에 추가하면 모든 xml이 레이아웃 별로 무조건 생성된다
만약 이 레이아웃은 바인딩 클래스가 필요 없다면 아래 속성을 루트 뷰에 추가하면된다.
<pre>
 <code>
 tools:viewBindingIgnore="true"
 </code>
</pre>

#### 3. 액티비티
각 xml 레이아웃 파일은 파일 이름을 카멜표기법으로 바꾸고 끝에 Binding을 붙인 바인딩 클래스가 생성된다.
(\_는 무시된다)
아래 코드가 main_activity.xml이라면 MainActivityBinding이라는 바인딩 클래스가 생성되고, 루트 뷰에 관한 직접 참조를
제공하는 getRoot() 메소드가 포함된다. 아래 코드에선 getRoot()메서드가 LinearLayout 루트 뷰를 반환하고,
id가 있는 Textview와 Button을 .name, .button으로 사용할 수 있다. (ImageView는 id가 없으므로 참조할 수 없다.)
Kotlin에서는 getRoot도 프로퍼티로 사용한다.
<pre>
 <code>
 //Activity
  private lateinit var binding : MainActivityBinding
  
  ovrride fun onCreate(savedInstanceState: Bundle){
    super.onCreate(savedInstanceState)
    binding = MainActivityBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    
    binding.name.text =  viewModel.name
    binding.button.setOnClickListener { viewModel.userClicked()}
  }
 
 //xml
<LinearLayout ... >
        <TextView android:id="@+id/name" />
        <ImageView android:cropToPadding="true" />
        <Button android:id="@+id/button"
            android:background="@drawable/rounded_button" />
    </LinearLayout>
 </code>
</pre>

#### 4. 프래그먼트
프래그먼트는 뷰보다 오래 지속되기 때문에, onDestroyView() 메서드에서 바인딩 클래스 인스턴스 참조를 해제해줘야 한다.
<pre>
 <code>
     private var _binding: ResultProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResultProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
 </code>
</pre>


<pre>
 <code>
 
 </code>
</pre>


<pre>
 <code>
 
 </code>
</pre>

<pre>
 <code>
 
 </code>
</pre>
    
    
    
