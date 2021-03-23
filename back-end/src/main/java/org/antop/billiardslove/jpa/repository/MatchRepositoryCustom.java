package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;

import java.util.List;

public interface MatchRepositoryCustom {

    /**
     * 내가 참가한 대회의 경기를 나를 기준으로 조회한다.
     *
     * @param contest 대회 정보
     * @param member  회원 정보
     * @return 경기 목록
     */
    List<Match> findJoinedIn(Contest contest, Member member);

}
