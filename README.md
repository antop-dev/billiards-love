# Billiards Love

[![GitHub issues](https://img.shields.io/github/issues/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love/issues)
[![GitHub forks](https://img.shields.io/github/forks/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love/network)
[![GitHub stars](https://img.shields.io/github/stars/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love/stargazers)
[![GitHub license](https://img.shields.io/github/license/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love)

당구사랑 동호회 대회 관리 사이트

## Front-End

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Afront-end&metric=alert_status)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Afront-end&metric=bugs)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Afront-end&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Afront-end&metric=code_smells)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Afront-end&metric=coverage)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Afront-end&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)

### Test

Jest 를 사용하여 `vue`, `js` 코드를 테스트 한다.

```
npm run test
```

**References**

* https://heropy.blog/2020/05/20/vue-test-with-jest/
* https://jestjs.io/docs/en/configuration

## Back-End

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=alert_status)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=bugs)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=code_smells)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=coverage)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)

### [Sentry](https://sentry.io/)

운영 중 발생한 에러를 관리한다.

프로젝트: https://sentry.io/organizations/antop/issues/?project=5351706

### [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot)

`application.yml` 내의 중요 정보를 암호화 한다.

암호화 비밀번호<sup>`encryption password`</sup>는 [RANDOM.ORG](https://www.random.org/strings) 에서 무작위 생성했다.

개발 시에는 아래와 같이 암호화 비밀번호를 파라미터에 추가 후 실행한다. 

![Intellij 암호화 비밀번호 설정](https://i.imgur.com/aXSRNu7.png)

## Continuous Integration (GitHub Actions)

### Node

Node 12.x 버전으로 빌드

### Java

Java 8 버전으로 빌드

###  SonarCloud

Java / JavaScript 코드를 테스트 후 SonarCloud 로 보고서<sup>`report`</sup>를 전송한다.

프로젝트

* [billiards-love:front-end](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end)
* [billiards-love:back-end](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)

**Issue:** https://github.com/antop-dev/billiards-love/issues/33 

## Database

ERDCloud 사용하여 작성. ERD 공유 선택이 **공개**, **개인**, **팀**이 있는데 팀+공개가 되었으면 좋겠다. 

* Team : https://www.erdcloud.com/team/3Rm8Lqv55MTLh2eXN
* ERD<sup>`Entity Relationship Diagram`</sup> : https://www.erdcloud.com/d/AyLdJKNJA4ratsoNm

**References**

* [변수명 짓기](https://www.curioustore.com/#!/util/naming)
* [H2 Data Types](http://www.h2database.com/html/datatypes.html)
* [MariaDB Data Types](https://mariadb.com/kb/en/data-types/)
