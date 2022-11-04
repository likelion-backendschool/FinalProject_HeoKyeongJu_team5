## Title: [3Week] 허경주

<br>

### 미션 요구사항 분석 & 체크리스트

아래는 요구사항 분석 내용 및 필요할 것으로 보이는 기능에 대한 정리이다.

__1. 관리자__   
   관리자는 authLevel을 7로 가진다. 이를 통해 관리자 권한을 파악하여, 권한이 있는 계정에 한하여 관리자 페이지에 접근이 가능하다.   
   관리자 페이지를 통하여 정산, 출금 처리 등의 작업을 할 수 있게 된다.  
- [x] AuthLevel 설정
- [x] Spring security 로 권한 부여
- [x] 관리자 페이지

__2. 정산__   
   건별정산처리, 전체(선택)정산처리를 진행한다.   
   편의상 PG 수수료는 0원으로 가정하고, 정산비율은 판매자와 멋북스가 5:5로 나눈다.   
- [x] 정산 데이터 생성
- [x] 개별 정산 처리
- [x] 선택/전체 정산 처리
- [x] 정산 페이지 (관리자)
- [ ] 정산 데이터 배치로 생성 (@EnableScheduling)

__3. 출금 신청 (사용자)__
- [x] entity 작성
- [x] 출금 신청 기능
- [x] 출금 신청 페이지
- [x] 본인 출금 신청 리스트 페이지


__4. 출금 처리 (관리자)__
- [ ] 출금 처리 기능
- [ ] 출금 처리 페이지
- [x] 전체 사용자 출금 신청 리스트

각 기능에 대한 체크리스트는 issue에도 작성하였으므로 [3주차 미션](https://github.com/likelion-backendschool/FinalProject_HeoKyeongJu_team5/issues/12) 등에서 확인이 가능하다.
  
<br>

----


### 3주차 미션 요약


**[접근 방법]**

 __**1. 관리자**__   
    
 > 관리자는 권한 부여 방식에 대해 고민했다. enum을 사용해 관리하기로 결정하였다.    
 > 권한은 AuthLevel이라는 enum 을 통해 관리한다.   
  
 *../domain/member/entity/AuthLevel.java*
 ```java
public enum AuthLevel {
  NORMAL(3, "NORMAL"),
  ADMIN(7, "ADMIN");

  AuthLevel(int code, String value) {
    this.code = code;
    this.value = value;
  }
  ...
}

private int code;
private String value;
...
 ```
    
   *../base/initData/InitDataBefore.java*

 ```java
Member admin = memberService.join("허경주", "1234", "beewt@naver.com",null);
admin.setAuthLevel(AuthLevel.ADMIN); // 관리자 권한 부여
``` 
    
   *../security/service/CustomUserDetailsService.java*

```java
if (member.getUsername().equals("허경주")) {
   authorities.add(new SimpleGrantedAuthority(AuthLevel.ADMIN.getValue()));
}
authorities.add(new SimpleGrantedAuthority(AuthLevel.NORMAL.getValue()));
```         

<br>
<br>

__**2. 정산**__   
    [이슈 - #14](https://github.com/likelion-backendschool/FinalProject_HeoKyeongJu_team5/issues/14)
    

<br>
<br>

__**3. 출금 신청 (사용자)**__   
   > 사용자는 프로필을 통해 예치금을 출금 페이지로 접근할 수 있다.   
   출금 페이지에서 본인이 신청한 모든 출금 신청을 확인 할 수 있으며, 이 페이지에서 출금 신청 페이지로 넘어갈 수 있다.
   출금 기능을 위해 사용자, 계좌번호, 은행명, 출금상태 등을 엔티티로 설정한다.   
   enum ApplyStatus는 READY_STATUS("처리중"),COMPLETE_STATUS("출금 완료")로 되어있다.
   
   > 본인이 가지고 있는 예치금을 넘어 출금 신청을 하지 않도록 하는 로직에 대해 신경썼다.
   
   
 *../domain/withdraw/entity/Withdraw.java*   
   
```java
@Table(name = "withdraw")
public class Withdraw extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank")
    private String bank;

    @Column(name = "withdraw_amount")
    private long withdrawAmount;

    @Column(name = "apply_status")
    private ApplyStatus applyStatus;
}
```

<br>
<br>

__4. 출금 처리 (관리자)__
> 출금 처리에 대해서는 어떻게 출금 서비스를 구현해야할지 감이 오지 않아 구현하지 못하였다.

<br>

----

**[특이사항]**   
정산에 관련하여서는 강사님과 수업시간에 진행했던 프로젝트를 기반으로 하였다.   
나머지는 기존에 작성했던 코드를 기반으로 엔티티, 서비스, 컨트롤을 작성했다.   
페이지는 기존 페이지에서 필요한 부분을 가져와 작성하였다.


[문제해결]   
사용자가 보는 리스트에서 순번이 전체 데이터의 id 값이 출력되는 문제가 있었다.
-> 타임리프에서 th:each, count를 써서 해결했다. (https://www.baeldung.com/thymeleaf-iteration)


<br>
