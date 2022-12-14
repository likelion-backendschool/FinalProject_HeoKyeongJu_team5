## Title: [1Week] 허경주

<br>

### 미션 요구사항 분석 & 체크리스트

매 주 제공되는 미션 별 요구사항을 기반으로 기능에 대한 분석을 진행한 후, 아래와 같은 체크리스트를 작성합니다. 

- ‘어떻게 개발을 진행 할 것인지에 대한 방향성’을 확인하는 과정이기 때문에 최대한 깊이있게 분석 후 진행해주시기 바랍니다.

<br>

### 1주차 미션 요약


**[접근 방법]**

1. Member
: 사용자와 관련하여 가장 고민했던 부분은 권한과 사용자 유형에 대해 어떻게 접근할까였다.
  지금까지 spring security를 사용하며 Role이라는 고정된 방식을 통해 관리자/일반에 대한 권한 차이를 주었었다.
  하지만 이번 프로젝트에서는 AuthLevel을 통해 관리자/일반을 구분하고, Type을 통해 작가/사용자를 구분하게 하였다.
  
  - 로그인, 로그아웃
      - 로그인과 로그아웃에 관한 것은 스프링 시큐리티를 통해 해결한다.
  - 회원가입
      - 회원가입을 마친 사용자는 자동으로 일반(AuthLevel:3)의 권한과 사용자(Type:USER) 자격이 되도록 한다.
      - 회원가입이 완료된 사용자에게 이메일 전송 : 미구현
  - 아이디 찾기 : 미구현
  - 내 정보 수정 : 미구현

2. Post
 : 작성한 내용을 html파일로 저장하는 방법은 commonmark를 이용하여 작성된 내용을 html로 파싱하여 저장하도록 하였다.
  - 해시태그 : 미구현
  - 마크다운 : 구현중
  - CRUD
  - 글 목록 
  - 상세페이지

각 기능에 대한 자세한 내용과 체크리스트는 issue에 작성하였으므로 https://github.com/likelion-backendschool/FinalProject_HeoKyeongJu_team5/issues 에서 확인이 가능하다.
    
<br>

**[특이사항]**

Entity에서는 Setter를 지향하고 Dto와 Vo를 이용하여 entity의 불변성을 가져가려고 했으나, 이를 지켜가며 구현하지 못해서 가장 큰 아쉬움이 남는다.
HashTag 기능에 대해서 어떻게 접근해야할지 명확한 정리를 하지 못해 개발 후순위로 두었고 이로 인하여 이 기능에 대한 개발을 완료하지 못하였다.

Post 관련하여 마크다운을 적용하고, 이를 html로 저장하는 것까지 구현하였으나 이를 상세페이지에서 나타나게 하지 못했다.
이에 대해 방법을 찾고 있으나 해결하지 못하고 있다.

이후 미완성된 기능들에 대한 구현뿐만 아니라 구조적으로 문제가 있을 수 있는 부분에 대해 확인하고 수정해나갈 예정이다.

전반적으로 아쉬운 부분은 초반 USER에 대한 설계르 할 때 요구사항 분석을 제대로 하지 못해 시간을 많이 소요하고 이로 인하여 기능을 많이 구현하지 못한 것이다.
하지만 그런 시간으로 인해 사용자의 권한, 역할 등에 대한 생각을 정리할 수 있었음에 의의를 둔다.
  
  <br>
