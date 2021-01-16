package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.api.KakaoDto;
import org.antop.billiardslove.api.MemberDto;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoggedInServiceImpl implements LoggedInService {

    private final KakaoRepository kakaoRepository;
    private final MemberRepository memberRepository;

    /**
     * 카카오 정보 조회
     *
     * @param kakaoDto 로그인된 카카오 정보
     * @return 카카오 사용자 정보
     */
    public KakaoLogin getKakaoInfo(KakaoDto kakaoDto) {

        return kakaoRepository.findById(kakaoDto.getId()).orElseGet(() -> kakaoRepository.save(KakaoLogin.builder()
                .id(kakaoDto.getId())
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder()
                        .nickname(kakaoDto.getNickname())
                        .imgUrl(kakaoDto.getImageUrl())
                        .thumbUrl(kakaoDto.getThumbnailUrl())
                        .build())
                .build()));
    }

    /**
     * 회원 조회
     *
     * @param kakaoLogin 카카오 사용자 정보
     * @param kakaoDto   로그인된 카카오 정보
     * @return 회원 정보
     */
    public MemberDto getMemberInfo(KakaoLogin kakaoLogin, KakaoDto kakaoDto) {
        Member member = memberRepository.findByKakaoLogin(kakaoLogin).orElseGet(() -> memberRepository.save(Member.builder()
                .nickname(kakaoDto.getNickname())
                .kakaoLogin(kakaoLogin)
                .build()));

        return MemberDto.builder()
                .id(member.getId())
                .nickname(kakaoDto.getNickname())
                .thumbnail(kakaoDto.getThumbnailUrl())
                .handicap(member.getHandicap())
                .build();
    }
}
