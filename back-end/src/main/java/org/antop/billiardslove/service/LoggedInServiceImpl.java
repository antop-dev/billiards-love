package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Kakao.Profile;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LoggedInServiceImpl implements LoggedInService {
    private final KakaoRepository kakaoRepository;
    private final MemberRepository memberRepository;

    public MemberDto loggedIn(KakaoDto kakaoDto) {
        Kakao kakaoLogin = kakaoRepository.findById(kakaoDto.getId()).orElseGet(() -> {
            Kakao newLogin = Kakao.builder()
                    .id(kakaoDto.getId())
                    .connectedAt(LocalDateTime.now())
                    .profile(Profile.builder()
                            .nickname(kakaoDto.getNickname())
                            .imgUrl(kakaoDto.getImageUrl())
                            .thumbUrl(kakaoDto.getThumbnailUrl())
                            .build())
                    .build();
            return kakaoRepository.save(newLogin);
        });

        kakaoLogin.changeProfile(Profile.builder()
                .nickname(kakaoDto.getNickname())
                .imgUrl(kakaoDto.getImageUrl())
                .thumbUrl(kakaoDto.getThumbnailUrl())
                .build());

        Member member = memberRepository.findByKakao(kakaoLogin).orElseGet(() -> {
            Member newMember = Member.builder()
                    .nickname(kakaoDto.getNickname())
                    .kakao(kakaoLogin)
                    .build();
            return memberRepository.save(newMember);
        });

        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .thumbnail(member.getKakao().getProfile().getThumbUrl())
                .handicap(member.getHandicap())
                .build();
    }

}
