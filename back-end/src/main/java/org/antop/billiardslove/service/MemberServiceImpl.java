package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements PrincipalProvider, MemberModifyService, MemberService {
    private final MemberRepository repository;

    @Transactional(readOnly = true)
    @Override
    public String getPrincipal(Object id) {
        Member member = repository.findById(Long.valueOf(id.toString()))
                .orElseThrow(MemberNotFountException::new);
        return member.getId().toString();
    }

    @Override
    public void modify(long id, String nickname, int handicap) {
        Member member = repository.findById(id).orElseThrow(MemberNotFountException::new);
        member.setNickname(nickname);
        member.setHandicap(handicap);
    }

    @Override
    public Member getMember(long memberId) {
        return repository.findById(memberId).orElseThrow(MemberNotFountException::new);
    }
}
