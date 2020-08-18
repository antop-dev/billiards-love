# Billiards Love

당구사랑 동호회 대회 관리 사이트

## Front-End

## Back-End

### [Sentry](https://sentry.io/)

운영 중 발생한 에러를 관리한다.

프로젝트: https://sentry.io/organizations/antop/issues/?project=5351706

### [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot)

`application.yml` 내의 중요 정보를 암호화 한다.

암호화 비밀번호<sup>`encryption password`</sup>는 [RANDOM.ORG](https://www.random.org/strings) 에서 무작위 생성했다.

개발 시에는 아래와 같이 암호화 비밀번호를 파라미터에 추가 후 실행한다. 

![Intellij 암호화 비밀번호 설정](https://i.imgur.com/aXSRNu7.png)

### SonarCloud

CI<sup>`Continuous Integration`</sup> 단계에서 소스코드 정적 분석을 수행한다.

프로젝트

* https://sonarcloud.io/dashboard?id=antop-dev_billiards-love

References

* https://sonarcloud.io/documentation/analysis/scan/sonarscanner-for-gradle/
* https://plugins.gradle.org/plugin/org.sonarqube

## Database

ERDCloud 사용하여 작성. ERD 공유 선택이 **공개**, **개인**, **팀**이 있는데 팀+공개가 되었으면 좋겠다. 

* Team : https://www.erdcloud.com/team/3Rm8Lqv55MTLh2eXN
* ERD<sup>`Entity Relationship Diagram`</sup> : https://www.erdcloud.com/d/AyLdJKNJA4ratsoNm

References

* [변수명 짓기](https://www.curioustore.com/#!/util/naming)
* [H2 Data Types](http://www.h2database.com/html/datatypes.html)
* [MariaDB Data Types](https://mariadb.com/kb/en/data-types/)
