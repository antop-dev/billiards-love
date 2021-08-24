package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface MemberMapper {

    @Mapping(source = Member.Fields.KAKAO
            + "." + Kakao.Fields.PROFILE
            + "." + Kakao.Profile.Fields.THUMB_URL,
            target = MemberDto.Fields.THUMBNAIL)
    MemberDto toDto(Member member);

}
