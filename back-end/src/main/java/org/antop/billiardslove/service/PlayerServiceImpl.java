package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;
    private final MemberService memberService;

    @Override
    public Player getPlayer(long memberId) {
        return repository.findByMember(memberService.getMember(memberId));
    }
}
