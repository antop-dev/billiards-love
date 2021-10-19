# Back-End

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=alert_status)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=bugs)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=code_smells)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=coverage)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_billiards-love%3Aback-end&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end)

### API 명세서

https://raw.githack.com/antop-dev/billiards-love/master/back-end/src/main/resources/static/index.html

### Generate class files

Mapstruct, Querydsl, Lombok 클래스 파일을 생성하기 위함.

```bash
./gradlew compileJava
```

### [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot)

개발 시 암호화 키를 지정해서 실행해야 한다.

[Wiki 참조](https://github.com/antop-dev/billiards-love/wiki/%EB%B0%B1%EC%97%94%EB%93%9C-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%8B%A4%ED%96%89-%EC%8B%9C-Jasypt-%EC%84%A4%EC%A0%95-%EC%B6%94%EA%B0%80%ED%95%98%EA%B8%B0)

### [Sentry](https://sentry.io/)

운영 중 발생한 에러를 관리한다.

* 문서: https://docs.sentry.io/platforms/java/guides/spring-boot/
* 프로젝트: https://sentry.io/organizations/antop/issues/?project=5351706
