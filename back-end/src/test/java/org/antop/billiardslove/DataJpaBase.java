package org.antop.billiardslove;

import com.github.javafaker.Faker;
import com.integralblue.log4jdbc.spring.Log4jdbcAutoConfiguration;
import org.antop.billiardslove.constants.Profiles;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

@DataJpaTest(showSql = false)
@ActiveProfiles(Profiles.TEST)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(Log4jdbcAutoConfiguration.class)
abstract public class DataJpaBase {

    protected static final Logger log = LoggerFactory.getLogger(DataJpaBase.class);

    /**
     * https://github.com/DiUS/java-faker
     */
    protected final Faker faker = new Faker(Locale.KOREA);
    /**
     * JPA
     */
    @PersistenceContext
    private EntityManager em;

    @AfterEach
    public void tearDown() {
        flushAndClear();
    }

    protected final void flush() {
        em.flush();
        log.info("flushed...");
    }

    protected void flushAndClear() {
        flush();
        em.clear();
    }

    /**
     * 카카오 스텁 생성
     *
     * @return 생성된 카카오 정보
     */
    protected final Kakao kakao() {
        return kakao(faker.number().numberBetween(9_000_000_000L, 9_999_999_999L));
    }

    protected final Kakao kakao(long id) {
        return Kakao.builder()
                .id(id)
                .connectedAt(LocalDateTime.now())
                .profile(Kakao.Profile.builder()
                        .nickname(faker.name().name().replaceAll(" ", ""))
                        .imgUrl("https://picsum.photos/640")
                        .thumbUrl("https://picsum.photos/110").build())
                .build();
    }

    /**
     * 회원 스텁 생성
     *
     * @return 회원 정보
     */
    protected final Member member() {
        return member(kakao());
    }

    protected final Member member(Kakao kakao) {
        return Member.builder()
                .nickname(faker.name().username())
                .kakao(kakao)
                .build();
    }

    /**
     * 선수 스텁 생성
     *
     * @param contest 대회 정보
     * @return 선수 정보
     */
    protected final Player player(Contest contest) {
        return player(contest, member());
    }

    protected final Player player(Contest contest, Member member) {
        return Player.builder()
                .contest(contest)
                .member(member)
                .handicap(faker.number().numberBetween(15, 40))
                .build();
    }

    /**
     * 대회 스텁 생성
     *
     * @return 대회 정보
     */
    protected final Contest contest() {
        return Contest.builder()
                .title("코로나 추석 리그전 " + faker.number().digits(4))
                .description("상금 : 갤러시 폴드 " + faker.number().digits(1))
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(faker.number().numberBetween(16, 256))
                .build();
    }

    /**
     * 경기 스텁 생성
     *
     * @param contest 대회 정보
     * @param player1 선수1 정보
     * @param player2 선수2 정보
     * @return 경기 정보
     */
    protected final Match match(Contest contest, Player player1, Player player2) {
        return Match.builder()
                .contest(contest)
                .player1(player1)
                .player2(player2)
                .build();
    }

}
