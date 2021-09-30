package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.CodeDto;
import org.antop.billiardslove.jpa.entity.Code;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public interface CodeMapper {

    @Mapping(source = "id", target = "code")
    @Mapping(source = "group.id", target = "group")
    CodeDto toDto(Code code);

}
