package org.antop.billiardslove.api;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.mapper.ContestMapper;
import org.antop.billiardslove.service.ContestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 대회 API
 *
 * @author jammini
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestInfoApi {
    private final ContestService contestService;
    private final ContestMapper contestMapper;

    /**
     * 대회 정보 한건 조회
     */
    @GetMapping("/api/v1/contest/{id}")
    public ContestDto info(@PathVariable(name = "id") long contestId) {
        return contestService.getContest(contestId).orElseThrow(ContestNotFoundException::new);
    }

    /**
     * 대회 목록 조회
     */
    @GetMapping("/api/v1/contests")
    public List<ContestDto> list() {
        return contestService.getAllContests();
    }

    /**
     * 대회 등록
     */
    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PostMapping("/api/v1/contest")
    public ResponseEntity<ContestDto> register(@RequestBody Model model, UriComponentsBuilder builder) {
        ContestDto contest = contestService.register(contestMapper.toDto(model));
        URI uri = builder.replacePath("/api/v1/contest/{id}").buildAndExpand(contest.getId()).toUri();
        return ResponseEntity.created(uri).body(contest);
    }

    /**
     * 대회 정보 수정
     */
    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PutMapping("/api/v1/contest/{id}")
    public ContestDto modify(@PathVariable(name = "id") long contestId,
                             @RequestBody Model model) {
        return contestService.modify(contestId, contestMapper.toDto(model));
    }

    /**
     * 대회정보 등록/수정 모델
     *
     * @author antop
     */
    @Getter
    @Builder
    @RequiredArgsConstructor
    @FieldNameConstants
    public static class Model {
        /**
         * 대회명
         */
        private final String title;
        /**
         * 대회 설명
         */
        private final String description;
        /**
         * 시작일
         */
        private final LocalDate startDate;
        /**
         * 시작시간
         */
        private final LocalTime startTime;
        /**
         * 종료일
         */
        private final LocalDate endDate;
        /**
         * 종료시간
         */
        private final LocalTime endTime;
        /**
         * 최대 참가 인원
         */
        private final Integer maxJoiner;
    }

}
