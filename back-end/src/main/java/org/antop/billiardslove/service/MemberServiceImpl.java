package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.antop.billiardslove.exception.MemberNotFountException;
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
        Member member = repository.findById(Long.valueOf(id.toString()))
                .orElseThrow(MemberNotFountException::new);
        return member.getId().toString();
    }
}
