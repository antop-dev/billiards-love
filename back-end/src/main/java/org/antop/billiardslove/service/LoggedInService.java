package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.antop.billiardslove.api.LoggedInRequest.Profile;

@Service
@RequiredArgsConstructor
public class LoggedInService {

    private final KakaoRepository kakaoRepository;
    private final MemberRepository memberRepository;

    /**
     * 카카오 로그인
     *
     * @param kakaoId 카카오 회원번호
     * @param profile 카카오 프로필
     * @return 회원
     */
    public Member join(Long kakaoId, Profile profile) {

        // 카카오 정보가 있으면 가져오고 없으면 저장
        KakaoLogin kakaoLogin = kakaoRepository.findById(kakaoId).orElseGet(() -> kakaoRepository.save(KakaoLogin.builder()
                .id(kakaoId)
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder().nickname(profile.getNickname()).imgUrl(profile.getImageUrl()).thumbUrl(profile.getThumbnailUrl()).build())
                .build()));

        return memberRepository.findByKakaoLogin(kakaoLogin).orElseGet(() -> memberRepository.save(Member.builder()
                .nickname(profile.getNickname())
                .kakaoLogin(kakaoLogin)
                .build()));

    }

}
