## 📌Builder Pattern
- 디자인 패턴
- 복합 객체의 생성 과정과 표현 방법을 분리하여 동일한 생성 절차에서 서로 다른 표현 결과를 만들 수 있게 하는 패턴(위키백과)
- 복합 객체의 구성 요소를 포함한 별도의 Build 클래스를 만들고, 이 Builder 클래스로 구성 요소를 조합하여 객체를 생성한다.
- 생성자 인자가 많아지면 가독성도 떨어지고 필요에 따라 여러 생성자를 정의해줘야 되는 번거로움을 보완
- 객체 생성 영역 분리
- 생성자 인자가 많을 경우 사용 고려
- .메소드들로 프로퍼티 설정(선택적)
- 생성자는 private -> 반드시 .build()로만 객체 생성
- 장점
  - 각 인자의 의미를 파악하기 쉽다(가독성)
  - 필수 매개변수, 선택적 매개변수를 통해 객체를 생성할 때 보다 유연하게 활용할 수 있다.(유연성)
- 단점 
  - 클래스 생성자나 멤버변수가 수정되면 빌더 클래스도 수정해야 함


```kotlin
class Student {
    private var name = ""
    private var gender = ""
    private var age = 0
    private var stuNum = 0

    class Builder(
        // 필수 매개변수
        val name: String,
        val stuNum: Int) {

        // 선택적 매개변수
        var age = 25
        var gender = ""

        //반드시 Builder를 리턴!
        fun age(age: Int): Builder {
            this.age = age
            return this
        }

        fun gender(gender: String): Builder {
            this.gender = gender
            return this
        }

        //반드시 build를 통해 객체 생성
        fun build(): Student {
            return Student(this)
        }
    }
    
    // Builder 패턴 생성자
    // 외부에서 생성 못하게
    private constructor(builder: Builder) {
        name = builder.name
        gender = builder.gender
        age = builder.age
        stuNum = builder.stuNum
    }
}

val student = Student.Builder("KYJ", 2015111111) // 필수 인자
    .age(17) // 선택적 인자
    .gender("남자") // 선택적 인자
    .build()

```

