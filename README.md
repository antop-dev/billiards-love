# Billiards Love

[![GitHub issues](https://img.shields.io/github/issues/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love/issues)
[![GitHub forks](https://img.shields.io/github/forks/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love/network)
[![GitHub stars](https://img.shields.io/github/stars/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love/stargazers)
[![GitHub license](https://img.shields.io/github/license/antop-dev/billiards-love)](https://github.com/antop-dev/billiards-love)

당구사랑 동호회 대회 관리 사이트

## Continuous Integration (GitHub Actions)

### Node

Node 12.x 버전으로 빌드

### Java

Java 8 버전으로 빌드

###  SonarCloud

Java / JavaScript 코드를 테스트 후 SonarCloud 로 보고서<sup>`report`</sup>를 전송한다.

* 이슈: https://github.com/antop-dev/billiards-love/issues/33
* 백앤드: https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Aback-end
* 프론트앤드: https://sonarcloud.io/dashboard?id=antop-dev_billiards-love%3Afront-end

### Database

ERD<sup>`Entity Relationship Diagram`</sup>는 DDL<sup>`Data Definition Language`</sup> 파일로 관리하며 다이어 그램은 각자의 툴로 본다. (예: 인텔리제이)

* 개발: H2 임베디드 데이터베이스를 사용한다.
* 운영: MariaDB 사용한다.

### References

* [변수명 짓기](https://www.curioustore.com/#!/util/naming)
* [H2 Data Types](http://www.h2database.com/html/datatypes.html)
* [MariaDB Data Types](https://mariadb.com/kb/en/data-types/)
