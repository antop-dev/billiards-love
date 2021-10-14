package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dao.PlayerDao;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.mapper.PlayerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayerDao playerDao;
    private final PlayerMapper playerMapper;

    /**
     * 선수 정보를 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     */
    public Optional<PlayerDto> getPlayer(long contestId, long memberId) {
        return playerDao.findByContestAndMember(contestId, memberId).map(playerMapper::toDto);
    }

    /**
     * 대회에 참가한 선수 목록을 조회한다.
     *
     * @param contestId 대회 아이디
     */
    public List<PlayerDto> getPlayers(long contestId) {
        return playerDao.findByContest(contestId)
                .stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

}
