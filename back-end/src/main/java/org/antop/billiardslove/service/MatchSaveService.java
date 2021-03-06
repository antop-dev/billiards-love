package org.antop.billiardslove.service;

import org.antop.billiardslove.jpa.entity.Match;

/**
 * 대진표 관련 서비스
 *
 * @author antop
 */
public interface MatchSaveService {

    /**
     * 대진표를 저장한다.
     *
     * @param match {@link Match} 대진표 정보
     */
    void save(Match match);

}
