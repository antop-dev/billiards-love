package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoggedInService {
    private final KakaoRepository kakaoRepository;
    private final MemberRepository memberRepository;

    /**
     * 카카오톡 로그인 처리
     *
     * @param kakaoDto 카카오 로그인 정보
     * @return 회원 정보
     */
    public MemberDto loggedIn(final KakaoDto kakaoDto) {
        Kakao kakao = kakaoRepository.findById(kakaoDto.getId()).orElseGet(() -> {
            Kakao newKakao = Kakao.builder()
                    .id(kakaoDto.getId())
                    .connectedAt(kakaoDto.getConnectedAt())
                    .profile(Kakao.Profile.builder()
                            .nickname(kakaoDto.getNickname())
                            .imgUrl(kakaoDto.getImageUrl())
                            .thumbUrl(kakaoDto.getThumbnailUrl())
                            .build())
                    .build();
            return kakaoRepository.save(newKakao);
        });

        kakao.changeProfile(Kakao.Profile.builder()
                .nickname(kakaoDto.getNickname())
                .imgUrl(kakaoDto.getImageUrl())
                .thumbUrl(kakaoDto.getThumbnailUrl())
                .build());

        Member member = memberRepository.findByKakao(kakao).orElseGet(() -> {
            Member newMember = Member.builder()
                    .nickname(kakao.getProfile().getNickname())
                    .kakao(kakao)
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
