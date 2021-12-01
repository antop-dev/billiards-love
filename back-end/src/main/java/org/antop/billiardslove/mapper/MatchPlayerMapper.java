package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.MatchPlayerDto;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.MatchResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface MatchPlayerMapper {

    @Mapping(source = "player.member.nickname", target = "nickname")
    @Mapping(target = "result", expression = "java(matchResult.toArrayString())")
    MatchPlayerDto toDto(Player player, MatchResult matchResult);

}
