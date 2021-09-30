package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.jpa.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface PlayerMapper {

    @Mapping(source = "member.nickname", target = "nickname")
    PlayerDto toDto(Player player);

}
