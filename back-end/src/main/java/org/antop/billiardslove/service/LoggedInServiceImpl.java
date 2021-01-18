package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
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
    public MemberDto registerMember(KakaoDto kakaoDto) {

        KakaoLogin kakaoLogin = kakaoRepository.findById(kakaoDto.getId()).orElseGet(() -> kakaoRepository.save(KakaoLogin.builder()
                .id(kakaoDto.getId())
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder()
                        .nickname(kakaoDto.getNickname())
                        .imgUrl(kakaoDto.getImageUrl())
                        .thumbUrl(kakaoDto.getThumbnailUrl())
                        .build())
                .build()));

        if (!kakaoLogin.getProfile().getNickname().equals(kakaoDto.getNickname()) ||
                !kakaoLogin.getProfile().getImgUrl().equals(kakaoDto.getImageUrl()) ||
                !kakaoLogin.getProfile().getThumbUrl().equals(kakaoDto.getThumbnailUrl())) {
            kakaoLogin.setProfile(KakaoProfile.builder()
                    .nickname(kakaoDto.getNickname())
                    .imgUrl(kakaoDto.getImageUrl())
                    .thumbUrl(kakaoDto.getThumbnailUrl())
                    .build());
            kakaoRepository.save(kakaoLogin);
        }

        Member member = memberRepository.findByKakaoLogin(kakaoLogin).orElseGet(() -> memberRepository.save(Member.builder()
                .nickname(kakaoDto.getNickname())
                .kakaoLogin(kakaoLogin)
                .build()));

        if (!member.getNickname().equals(kakaoDto.getNickname())) {
            member.setNickname(kakaoDto.getNickname());
        }

        return MemberDto.builder()
                .id(member.getId())
                .nickname(kakaoDto.getNickname())
                .thumbnail(kakaoDto.getThumbnailUrl())
                .handicap(member.getHandicap())
                .build();
    }
}
