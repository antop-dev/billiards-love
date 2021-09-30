package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.jpa.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface MemberMapper {

    @Mapping(source = "kakao.profile.thumbUrl", target = "thumbnail")
    MemberDto toDto(Member member);

}
