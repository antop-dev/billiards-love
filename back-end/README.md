# Back-End

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=alert_status)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=bugs)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=code_smells)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=coverage)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)

### Generate Querydsl class files

```bash
./gradlew compileJava
```

### [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot)

`application.yml` 파일 내용에서 중요 정보를 암호화 한다.

암호화 비밀번호<sup>`encryption password`</sup>는 [RANDOM.ORG](https://www.random.org/strings) 에서 무작위 생성했다.

개발 시에는 아래와 같이 암호화 비밀번호를 파라미터에 추가 후 실행한다.

![Intellij 암호화 비밀번호 설정](https://i.imgur.com/aXSRNu7.png)

### [Sentry](https://sentry.io/)

운영 중 발생한 에러를 관리한다.

* 문서: https://docs.sentry.io/platforms/java/guides/spring-boot/
* 프로젝트: https://sentry.io/organizations/antop/issues/?project=5351706
