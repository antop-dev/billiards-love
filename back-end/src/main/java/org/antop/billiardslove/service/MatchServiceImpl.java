package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchSaveService {
    private final MatchRepository matchRepository;

    @Transactional
    @Override
    public void save(Match match) {
        matchRepository.save(match);
    }

}
