package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface KakaoMapper {
    @Mapping(source = KakaoDto.Fields.NICKNAME,
             target = Kakao.Fields.PROFILE
                    + "." + Kakao.Profile.Fields.NICKNAME)
    @Mapping(source = KakaoDto.Fields.IMAGE_URL,
             target = Kakao.Fields.PROFILE
                    + "." + Kakao.Profile.Fields.IMG_URL)
    @Mapping(source = KakaoDto.Fields.THUMBNAIL_URL,
             target = Kakao.Fields.PROFILE
                    + "." + Kakao.Profile.Fields.THUMB_URL)
    Kakao toEntity(KakaoDto kakaoDto);
}
