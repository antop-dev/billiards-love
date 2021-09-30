package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface KakaoMapper {
    @Mapping(target = "profile", ignore = true)
    @Mapping(source = "nickname", target = "profile.nickname")
    @Mapping(source = "imageUrl", target = "profile.imgUrl")
    @Mapping(source = "thumbnailUrl", target = "profile.thumbUrl")
    Kakao toEntity(KakaoDto kakaoDto);
}
