#DI Framework
## 의존성 주입?
- 의존성 주입(DI, Dependency Injection) : 하나의 객체에 다른 객체의 의존성을 제공하는 기술
- 하나의 객체에 다른 객체의 의존성을 제공하는 기술
## Dagger2 vs Koin vs Hilt
### Dagger2
- 현재 가장 많이 사용하는 DI Framework
- 러닝커브가 높음
- 컴파일 시 어노테이션을 읽고 scope에 맞는 DI container와 주입할 class파일을 generate하여 의존성 주입
- 컴파일 시점에서 에러를 검출하기 때문에 빌드가 완료된 파일은 의존성 주입에 대해 안정성 보장
- Java, Kotlin 프로젝트에서 사용 가능

### Koin
- Kotlin DSL(Domain Specific Language, 도메인 특화 언어)로 만들어짐
- Kotlin 개발 환경에 쉽게 적용할 수 있는 경량화된 DI Framework로, 가볍고 빠르게 적용할 수 있음
- 러닝커브가 낮음 (Dagger2에 비해 사용법이 쉽다)
- DSL을 활용하여 runtime에 의존성을 주입
- 따라서 compile 시점에서 오류 확인이 어렵고 runtime error가 발생할 수 있다.
- -> test를 잘 작성하여 앱의 퀄리티를 보장하자
- 의존성 주입의 입문이라 생각하고 추후에 다른 프레임워크로 마이그레이션하자!


### Hilt
- Dagger를 기반으로 만들어진 DI Framework로 Dagger2와 비슷한 동작방식
- 현재 구글에서 밀고 있는 framework로 적용 후기를 보면 평이 좋다.
- 러닝커브가 낮고 Dagger의 장점을 모았다.
- 컴파일 시점에서 에러 검출
- Koin으로 시작하고 Dagger는 공부만 하고 Hilt를 적용할 듯 싶다.



### References 
https://velog.io/@sysout-achieve/Android-DI-Framework-%EC%84%A0%ED%83%9D%EC%A7%80Dagger2-Koin-Hilt
