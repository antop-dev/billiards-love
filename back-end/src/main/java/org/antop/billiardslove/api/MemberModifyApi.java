package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.antop.billiardslove.constants.Security;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.service.MemberService;
import org.antop.billiardslove.util.Aes256Util;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 회원 정보 수정 API
 *
 * @author antop
 */
@RequiredArgsConstructor
@RestController
public class MemberModifyApi {
    private final MemberService service;

    @PutMapping("/api/v1/member")
    public MemberDto modify(@RequestBody @Valid Request request,
                            @AuthenticationPrincipal Long memberId,
                            @SessionAttribute(Security.SECRET_KEY) String secretKey) {
        MemberDto member = service.modify(
                memberId,
                Aes256Util.decrypt(request.getNickname(), secretKey),
                request.getHandicap()
        );
        // 별명을 암호화 한다.
        return MemberDto.builder()
                .id(member.getId())
                .nickname(Aes256Util.encrypt(member.getNickname(), secretKey))
                .thumbnail(member.getThumbnail())
                .handicap(member.getHandicap())
                .build();
    }

    /**
     * 회원정보 수정 요청
     *
     * @author antop
     */
    @Getter
    @ToString
    @FieldNameConstants
    static class Request {
        /**
         * 회원 별명
         */
        @NotBlank(message = "별명을 입력해주세요.")
        private final String nickname;
        /**
         * 핸디캡
         */
        @NotNull(message = "핸디캡을 입력해주세요.")
        @Min(value = 1, message = "핸디캡을 1 이상 입력해주세요.")
        private final Integer handicap;

        @JsonCreator
        public Request(@JsonProperty String nickname,
                       @JsonProperty Integer handicap) {
            this.nickname = nickname;
            this.handicap = handicap;
        }
    }

}
