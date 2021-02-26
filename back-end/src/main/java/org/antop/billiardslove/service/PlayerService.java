package org.antop.billiardslove.service;

import org.antop.billiardslove.jpa.entity.Player;

public interface PlayerService {
    /**
     * 플레이어 정보 조회
     *
     * @param memberId 회원아이디
     * @return 플레이어 정보
     */
    Player getPlayer(long memberId);
}
