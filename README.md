
# 본론부터 말하자면...
### 개인사유로 스프링부트를 듣지 못하고 바로 미션에 들어가다보니 할줄 아는거 없이 따라치기 말고는 답이없었다. 심지어 게시판 만들기 강의를 찾아서 하다보니 전혀 미션과 다른 방식의 강의였고 데이터베이스도 달라서 조금이라도 배운 SQLite 활용해보려다가 하루만 날려먹었다. 제대로 못외워서 그렇겠지..아무튼 그냥 망해서 일기장처럼 적었다.

#### 아무것도 모르고 급하게 억지로 꾸역꾸역 만들다보니 사실 과정이나 이해도나 머리에 남은건 5%도 안될것같다. 일단 그냥 뭔 역할인지도 모르는 글자들만 좀 익숙하게 봤던 시간으로 마무리



## 시작도 전에 0번째 문제
### 첫 데이터베이스 연결
 `프로젝트를 실행 중 만든 데이터(게시글, 댓글 등)는 프로젝트를 종료해도 기록이 남아 있어야 한다.` 
 
 라는 주의사항이 있는데 이는 데이터베이스를 이용하여 정보들을 저장해 프로젝트를 종료해도 기록을 남겨 다음에 다시 볼 수있도록 하는것 같다.
 
  수업때는 데이터 베이스로 SQLite 를 사용했지만..게시판을 만들기 위한 구글링 으로 찾아보니 다른 DB를 사용하는 사람들이 있어 나는 DB만 SQLite로 해야지 하고 이것저것 하루 종일 시도했지만 다 실패하고 하루 시간만 날린 후 결국 `MariaDB`를 사용하게 됐다. 커리큘럼에 보니 어차피 MariaDB가 있길래 그리하게 됐다.

## 첫번째 문제

  ### 수업들을 놓치며...
  여러가지 개인 사정들로 인해 본 강의들을 놓치고 독학을 하였고 게시판을 만들기를 하기엔 배운것이 하나도 없었다.

  그래서..

  구글링과 유튜브를 통해서 그나마 배운것들과 유튜브를 합쳐서 만들어보려 했지만 전혀 연동이 안되고 개고생고생 하다가 결국 포기하고 유튜브를 100% 보고 배워 만드니 작동이 드디어 된다..

  무엇이 옳고 틀린지는 전혀 모르겠으나 일단 만들어보자

## 두번째 문제
시작하자마자 제목과 내용을 작성 후 데이터를 받아야하는데 인강과는 다르게 이렇게 뜬다..무엇이 잘못된 것일까..
![Alt text](<문제 발생1.png>)
 

 자세히 에러난 부분을 보니 서블렛 어쩌구...
 `Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.IllegalArgumentException: Name for argument of type [java.lang.String] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag.] with root cause` 이런 에러가 나서 구글링을 했다.


 ### 해결
처음 인강에서는 빌드를 인텔리제이로 실행되게끔 아래의 그레이들을 인텔리제이로 바꾸어서 했다. (아마 멋사에서도 그랬던것같은데...)

![Alt text](<문제 발생 1-2.png>)

구글링 결과 빌드 및 실행,테스트 둘다다시 `Gradle`로 바꿔주니 작동이 되었다.


### 원인(?)

#### IntelliJ IDEA Build
인텔리제이 자체에서 제공하는 빌드 자동화 도구.
IntelliJ 빌드 시스템은 `IntelliJ의 자체 빌드 메커니즘으로 단순하게 프로젝트의 모든 수정 내용과 종속 파일을 컴파일`하는 기능이다.
여기서 문제는 수정 내용과 종속된 파일만 빌드한다는데 있다는 것.
안타깝게도 Maven과 Gradle과 달리 순수하게 빌드를 수행한다.
그로 인해 아티팩트 생성, 리포지토리 배포, CodeGen 등과 같은 다른 작업을 수행하지 않는다.

위와 같은 이유로 인해 대부분의 개발자들은 Maven과 Gradle과 같은 빌드 툴을 사용한다고 한다.
그리고 둘의 가장 큰 차이점은 증분 빌드 이냐 아니냐 라는 것

#### 증분 빌드(Incremental build)

증분된 부분, 즉 `변경된 부분만 빌드를 하는 방식`으로 변경되지 않은 것에 대해서는 건너뛰고 빌드를 진행해서 `빠른 빌드를 원할 경우 선택`하는 방법이다.

`IntelliJ IDEA가 바로 증분 빌드`인데. -> 그래서 IntelliJ IDEA가 Gradle 빌드 방식보다 빠르게 빌드를 수행할 수 있었던 것.

하지만 `증분 빌드`이므로 IntelliJ IDEA 빌드 방식은 "최신이다" 라고 판단하여 건너뛰고 빌드를 진행해, 빌드를 진행하고 나온 결과물에 실제로 `변경된 부분이 반영되지 않고 빌드가 완료될 수 있다.`(두 번째 참고사이트에서 이미 삭제한 파일에 대해서 변경사항이 없다고 판단해 삭제됐던 파일이 그대로 포함된 상태로 빌드가 완료되었다.)

따라서, 정확한 빌드를 원한다면 IntelliJ IDEA 빌드 방식이 아닌 Gradle 빌드 방식을 선택해야 한다.

#### Gradle Build
오픈 소스 빌드 자동화 도구.

Gradle은 build.gradle 파일을 사용하며 Maven과 동일하게 개발자가 사용할 라이브러리를 정의해 둘 수 있다.
`Gradle 역시 정의된 라이브러리뿐 아니라 그 라이브러리를 사용하는데 필요한 종속된 다른 라이브러리까지 관리해 자동으로 다운로드`하여 사용할 수 있게 해 준다.


## 세번째 문제..

### 인강을 통해서 간단한 기능정도를 만들기는 했지만 미션의 절반도 없이 끝나버렸다. 정작 미션으로 해결해야할 몇가지 목록을 해야하는데 약 2년이상된 영상이라 그랬는지 멋사에서 진행한 방식과는 좀 많이 달랐다.
ex. Dao나 Dto 등 , application.yaml 같은걸 쓰지 않고 원초적? 방식으로 한것같기도...

아아아주 기본적인 게시판 기능만 구현하고 필수 과제 절반도 하지 못함...
### 그러다 보니 새로 구글링을 통해서 필요한 을 찾아서 다시 마무리 해야한다.

###### 솥됐다..대부분 내가 만든 방식과는 다른 appliation.yml 에 Dao,Dto 등 사용해서 따라칠수도 없는 상황이다. 미션 중단!





