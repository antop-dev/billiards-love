package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.antop.billiardslove.dao.MemberDao;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements PrincipalProvider {
    private final MemberDao memberDao;
    private final MemberMapper memberMapper;

    @Transactional(readOnly = true)
    @Override
    public Principal getPrincipal(Object id) {
        long memberId = Long.parseLong(id.toString());
        return memberDao.findById(memberId)
                .map(member -> new Principal(member.getId(), member.isManager()))
                .orElseThrow(MemberNotFoundException::new);
    }

    /**
     * 회원 정보를 수정한다.
     *
     * @param memberId 회원 아이디
     * @param nickname 변경할 회원 별명
     * @param handicap 변경할 회원 핸디캡
     */
    @Transactional
    public MemberDto modify(long memberId, String nickname, int handicap) {
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.setNickname(nickname);
        member.setHandicap(handicap);

        return memberMapper.toDto(member);
    }

    /**
     * 회원 정보 조회
     *
     * @param memberId 회원 아이디
     * @return 회원 정보
     */
    public Optional<MemberDto> getMember(long memberId) {
        return memberDao.findById(memberId).map(memberMapper::toDto);
    }

}
