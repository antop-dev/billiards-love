package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.antop.billiardslove.handler.MemberNotFountException;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements PrincipalProvider {
    private final MemberRepository repository;

    @Override
    public String getPrincipal(Object id) {
        final long memberId = Long.parseLong(id.toString());
        Member member = repository.findById(memberId).orElseThrow(() -> new MemberNotFountException(memberId));
        return member.getId().toString();
    }

}
