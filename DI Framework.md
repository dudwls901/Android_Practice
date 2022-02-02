# DI Framework
## 의존성 주입?
- 의존성 주입(DI, Dependency Injection) : 하나의 객체에 다른 객체의 의존성을 제공하는 기술
- 컴포넌트 간의 의존 관계를 소스코드 내부가 아닌 외부 설정 파일 등을 통해 정의되게 한느 디자인 패턴
- 객체를 직접 코드로 생성하지 않고 외부에서 주입한 객체를 사용하는 방식
- 기존엔 직접 객체를 생성해서 각 인스턴스간 의존성을 가지게 되어 객체간 커플링이 생겼음
- DI를 이용하면 인스턴스 간 디커플링을 만들어줌 -> 유닛테스트 용이성 증대
## Dagger2 vs Koin vs Hilt
### Dagger2
- 현재 가장 많이 사용하는 DI Framework
- 러닝커브가 높음
- 컴파일 시 어노테이션을 읽고 scope에 맞는 DI container와 주입할 class파일을 generate하여 의존성 주입
- 컴파일 시점에서 에러를 검출하기 때문에 빌드가 완료된 파일은 의존성 주입에 대해 안정성 보장
- Java, Kotlin 프로젝트에서 사용 가능

### Koin
- Kotlin DSL(Domain Specific Language, 도메인 특화 언어)로 만들어짐
- Kotlin 개발 환경에 쉽게 적용할 수 있는 경량화된 DI Framework로, 가볍고 빠르게 적용할 수 있음(정확한 분류는 서비스 로케이터)
- 러닝커브가 낮음 (Dagger2에 비해 사용법이 쉽다)
- DSL을 활용하여 runtime에 의존성을 주입
- 컴파일 타임에 주입대상을 선정하는 DI에 비해 런타임에 서비스 로케이팅을 통해 인스턴스를 동적으로 주입해주기 때문에 런타임 퍼포먼스가 떨어짐
- 별도의 어노테이션을 사용하지 않기 때문에 컴파일 시간이 단축
- ViewModel 주입을 쉽게할 수 있는 벼로드이 라이브러리를 제공
- 따라서 compile 시점에서 오류 확인이 어렵고 runtime error가 발생할 수 있다.
- ex) 런타임시 주입이 필요한 컴포넌트가 생성이 되어있지 않는 파라미터가 있는 경우 크래시 발생
- -> test를 잘 작성하여 앱의 퀄리티를 보장하자
- 의존성 주입의 입문이라 생각하고 추후에 다른 프레임워크로 마이그레이션하자!
- module{...} : 키워드로 주입받고자 하는 객체의 집합
- single{...} : 앱이 실행되는 동안 계속 유지되는 싱글톤 객체를 생성
- factory{...} : 요청할 때마다 매번 새로운 객체를 생성
- get() : 컴포넌트 내에서 알맞은 의존성을 주입
- 외에도 프래그먼트나 액티비티에서 필요한 viewmodel을 주입받을 때는 viewmodel 키워드 이용


### Hilt
- Dagger를 기반으로 만들어진 DI Framework로 Dagger2와 비슷한 동작방식
- 현재 구글에서 밀고 있는 framework로 적용 후기를 보면 평이 좋다.
- Hilt 사용 권장
- 러닝커브가 낮고 Dagger의 장점을 모았다.
- 컴파일 시점에서 에러 검출
- Koin으로 시작하고 Dagger는 공부만 하고 Hilt를 적용할 듯 싶다.



### References 
https://velog.io/@sysout-achieve/Android-DI-Framework-%EC%84%A0%ED%83%9D%EC%A7%80Dagger2-Koin-Hilt
