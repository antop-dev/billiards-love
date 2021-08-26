package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dao.KakaoDao;
import org.antop.billiardslove.dao.MemberDao;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoggedInService {
    private final KakaoDao kakaoDao;
    private final MemberDao memberDao;
    private final MemberMapper memberMapper;

    /**
     * 카카오톡 로그인 처리
     *
     * @param kakaoDto 카카오 로그인 정보
     * @return 회원 정보
     */
    public MemberDto loggedIn(final KakaoDto kakaoDto) {
        Kakao kakao = kakaoDao.findById(kakaoDto.getId()).orElseGet(() -> {
            Kakao newKakao = Kakao.builder()
                    .id(kakaoDto.getId())
                    .connectedAt(kakaoDto.getConnectedAt())
                    .profile(Kakao.Profile.builder()
                            .nickname(kakaoDto.getNickname())
                            .imgUrl(kakaoDto.getImageUrl())
                            .thumbUrl(kakaoDto.getThumbnailUrl())
                            .build())
                    .build();
            return kakaoDao.save(newKakao);
        });

        kakao.changeProfile(Kakao.Profile.builder()
                .nickname(kakaoDto.getNickname())
                .imgUrl(kakaoDto.getImageUrl())
                .thumbUrl(kakaoDto.getThumbnailUrl())
                .build());

        Member member = memberDao.findByKakao(kakao).orElseGet(() -> {
            Member newMember = Member.builder()
                    .nickname(kakao.getProfile().getNickname())
                    .kakao(kakao)
                    .build();
            return memberDao.save(newMember);
        });
        return memberMapper.toDto(member);
    }
}
