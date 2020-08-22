[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love&metric=alert_status)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love&metric=bugs)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love&metric=code_smells)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love&metric=coverage)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love)

# Billiards Love

당구사랑 동호회 대회 관리 사이트

## Front-End

### Test

Jest 를 사용하여 `vue`, `js` 코드를 테스트 한다.

```
npm run test
```

**References**

* https://heropy.blog/2020/05/20/vue-test-with-jest/
* https://jestjs.io/docs/en/configuration

## Back-End

### [Sentry](https://sentry.io/)

운영 중 발생한 에러를 관리한다.

프로젝트: https://sentry.io/organizations/antop/issues/?project=5351706

### [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot)

`application.yml` 내의 중요 정보를 암호화 한다.

암호화 비밀번호<sup>`encryption password`</sup>는 [RANDOM.ORG](https://www.random.org/strings) 에서 무작위 생성했다.

개발시에는 아래와 같이 암호화 비밀번호를 파라미터에 추가 후 실행한다. 

![Intellij 암호화 비밀번호 설정](https://i.imgur.com/aXSRNu7.png)

## Continuous Integration (GitHub Actions)

### Node

Node 12.x 버전으로 빌드

### Java

Java 8 버전으로 빌드

###  SonarCloud

Java / JavaScript 코드를 테스트 후 SonarCloud 로 보고서<sup>`report`</sup>를 전송한다.

프로젝트: https://sonarcloud.io/dashboard?id=antop-dev_billiards-love

**References**

* https://sonarcloud.io/documentation/analysis/scan/sonarscanner-for-gradle/
* https://plugins.gradle.org/plugin/org.sonarqube
* https://www.npmjs.com/package/jest-sonar-reporter
