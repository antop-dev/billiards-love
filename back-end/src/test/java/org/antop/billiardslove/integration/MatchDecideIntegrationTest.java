package org.antop.billiardslove.integration;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.TestSecurityConfig;
import org.antop.billiardslove.api.MatchApi;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.dao.PlayerDao;
import org.antop.billiardslove.model.Outcome;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import util.JsonUtils;

import static org.antop.billiardslove.model.Outcome.LOSE;
import static org.antop.billiardslove.model.Outcome.WIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class MatchDecideIntegrationTest extends SpringBootBase {
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("경기 확정 통합 테스트")
    @Test
    void decide() throws Exception {
        /*
         * given
         */
        // 매니저 권한의 회원 아이디
        String token = jwtTokenProvider.createToken(1L);
        /*
         * when
         */

        /*
         * 선수1과 선수4가 경기해서 플레이어4가 3승
         * 선수1 = 150 + 3 = 153
         * 선수4 = 20 + 9 = 29
         */
        mockMvc.perform(post("/api/v1/match/3/decide")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(MatchApi.MatchDecideRequest.builder()
                                .left(new Outcome[]{LOSE, LOSE, LOSE})
                                .right(new Outcome[]{WIN, WIN, WIN})
                                .build())))
                .andExpect(status().isOk());
        /*
         * 선수4와 선수5가 경기해서 선수4가 3승
         * 선수4 = 29 + 9 = 38
         * 선수5 = 30 + 3 = 33
         */
        mockMvc.perform(post("/api/v1/match/10/decide")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(MatchApi.MatchDecideRequest.builder()
                                .left(new Outcome[]{WIN, WIN, WIN})
                                .right(new Outcome[]{LOSE, LOSE, LOSE})
                                .build())))
                .andExpect(status().isOk());
        flushAndClear();
        /*
         * then
         */

        playerDao.findById(1L).ifPresent(it -> { // 선수1
            assertThat(it.getRank(), is(1));
            assertThat(it.getScore(), is(153));
            assertThat(it.getVariation(), is(0));
        });
        playerDao.findById(4L).ifPresent(it -> { // 선수4
            assertThat(it.getRank(), is(3));
            assertThat(it.getScore(), is(38));
            assertThat(it.getVariation(), is(1));
        });
        playerDao.findById(5L).ifPresent(it -> { // 선수5
            assertThat(it.getRank(), is(4));
            assertThat(it.getScore(), is(33));
            assertThat(it.getVariation(), is(-1));
        });
    }
}
