package org.antop.billiardslove.constants;

/**
 * 두가지 개발환경을 사용한다.
 *
 * @author antop
 */
public class Profiles {
    private Profiles() {
    }

    /**
     * 로컬 환경 프로파일 (default)
     */
    public static final String LOCAL = "default";
    /**
     * 운영 환경 프로파일
     */
    public static final String PRODUCTION = "prod";
    /**
     * 테스트 환경 프로파일
     */
    public static final String TEST = "test";
}
