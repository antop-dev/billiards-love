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

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LoggedInServiceImpl implements LoggedInService {

    private final KakaoRepository kakaoRepository;
    private final MemberRepository memberRepository;

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

        kakaoLogin.changeProfile(KakaoProfile.builder()
                .nickname(kakaoDto.getNickname())
                .imgUrl(kakaoDto.getImageUrl())
                .thumbUrl(kakaoDto.getThumbnailUrl())
                .build());

        Member member = memberRepository.findByKakaoLogin(kakaoLogin).orElseGet(() -> memberRepository.save(Member.builder()
                .kakaoLogin(kakaoLogin)
                .build()));

        member.setNickname(kakaoDto.getNickname());

        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .thumbnail(member.getKakaoLogin().getProfile().getThumbUrl())
                .handicap(member.getHandicap())
                .build();
    }
}
