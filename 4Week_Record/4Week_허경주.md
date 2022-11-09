## Title: [NWeek] 김멋사

### 미션 요구사항 분석 & 체크리스트

**TODO**
- [x] swagger 적용하기
- [x] rest api 로그인 w/ JWT
- [ ] 내 도서 리스트 구현 (구현중)
- [ ] 도서 하나 보여주기 구현 (미구현)
- [x] 로그인 회원 정보 구현
- [x] 엑세스 토큰 화이트리스트 구현
- [ ] React로 구현된 프론트와 연결 (미시도)

<br>

### N주차 미션 요약

**[접근 방법]**

Rest api를 구현함에 있어, 우선적으로 restful한 것이 어떤 것을 의미하는지 공부하는 시간을 가졌다.
그 이후 로그인, 스웨거 적용, 도서 리스트, 로그인 회원정보 구현 등의 순서로 진행하였다.

구현을 하며 가장 신경을 썼던 부분은 응답으로 보낼 json 객체를 어떻게 처리할 것인가였다.
이는 중간 팀 프로젝트에서 React + Rest Api 를 구현했었기 때문에, 프로젝트 때 사용했던 model mapping을 이용하여 해결하였다.
    
<br>    

**[특이사항]**

**1. mapping, json 관련 문제**

원하는 데이터만을 포함한 Json을 보내기 위해 class를 만들고, 이를 mapping하는 방식으로 구현하였다.
이 과정에서 해결되지 않은 부분들이 있었다.
```java
public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(STRICT).setSkipNullEnabled(true);
        mapper.typeMap(MyBook.class, MyBooksResponse.class).addMappings(m -> {
            m.map(source -> source.getId(), MyBooksResponse::setId);
            m.map(source -> source.getCreateDate(), MyBooksResponse::setCreateDate);
            m.map(source -> source.getModifyDate(), MyBooksResponse::setModifyDate);
            m.map(source -> source.getOwner(), MyBooksResponse::setOwner); // here!
            m.map(source -> source.getProduct(), MyBooksResponse::setProduct);
        });
        ...
``` 
위 코드에서 소유한 회원의 id만 받아오고 싶었으나, ```  m.map(source -> source.getOwner().getId(), MyBooksResponse::setOwnerId); ``` 와 
MyBooksResponse의 변수도 맞게 설정했지만 _java.lang.long.longvalue()_ 와 같은 오류가 계속되어 어쩔 수 없이 소유 회원 정보 모두를 받아오는 위와 같은 코드로 마무리하였다.

<br>

**2. swagger 관련 문제**

<img width="1440" alt="image" src="https://user-images.githubusercontent.com/27273017/200763854-577f9407-db3c-4a4e-85ce-5d65723efc00.png">

위의 사진은 현재 swagger의 상태인데, ```admin-rebate-controller``` 가 추가되어 있는 것을 볼 수 있다.
이 경우 makeData 메소드에 ```@ResponseBody```이 있어 자동으로 추가된 것으로 예상하고 있다. 
하지만 rest api와 관련되지 않아 이 부분은 추후 리팩토링할 때 제외시키도록 할 예정이다.
