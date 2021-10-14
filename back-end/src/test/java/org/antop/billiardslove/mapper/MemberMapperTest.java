package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.Profiles;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@ActiveProfiles(Profiles.TEST)
class MemberMapperTest {
    @Autowired
    private MemberMapper mapper;

    @Test
    void toDto() {
        Member member = Member.builder()
                .nickname("안탑이야")
                .kakao(Kakao.builder()
                        .profile(Kakao.Profile.builder()
                                .imgUrl("https://cataas.com/cat")
                                .thumbUrl("https://dog.ceo/api/breeds/image/random")
                                .build()
                        )
                        .build()
                )
                .build();
        member.setHandicap(21);

        MemberDto dto = mapper.toDto(member);

        assertThat(dto, notNullValue());
        assertThat(dto.getNickname(), is(member.getNickname()));
        assertThat(dto.getHandicap(), is(member.getHandicap()));
        assertThat(dto.getThumbnail(), is(member.getKakao().getProfile().getThumbUrl()));
    }
}
