package org.antop.billiardslove.mapper;

import org.antop.billiardslove.api.ContestInfoApi;
import org.antop.billiardslove.constants.CodeGroups;
import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.CodeDto;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.CodeService;
import org.antop.billiardslove.service.PlayerService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public abstract class ContestMapper {
    private static final String MAPPING_STATE_CODE = "stateCode";
    private static final String MAPPING_STATE_NAME = "stateName";
    private static final String MAPPING_PLAYER = "player";

    @Autowired
    private PlayerService playerService;
    @Autowired
    private CodeService codeService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentJoiner", ignore = true)
    @Mapping(target = "stateCode", ignore = true)
    @Mapping(target = "stateName", ignore = true)
    @Mapping(target = "progress", ignore = true)
    @Mapping(target = "player", ignore = true)
    public abstract ContestDto toDto(ContestInfoApi.Model model);

    public ContestDto toDto(Contest contest) {
        return toDto(contest, null);
    }

    @Mapping(source = "state", target = "stateCode", qualifiedByName = MAPPING_STATE_CODE)
    @Mapping(source = "state", target = "stateName", qualifiedByName = MAPPING_STATE_NAME)
    @Mapping(source = "id", target = "player", qualifiedByName = MAPPING_PLAYER)
    public abstract ContestDto toDto(Contest contest, @Context Long memberId);

    @Named(MAPPING_STATE_CODE)
    String stateCode(ContestState state) {
        return state.getCode();
    }

    @Named(MAPPING_STATE_NAME)
    String stateName(ContestState state) {
        return codeService.getCode(CodeGroups.CONTEST_STATE, state.getCode())
                .map(CodeDto::getName)
                .orElse(null);
    }

    @Named(MAPPING_PLAYER)
    PlayerDto player(long contestId, @Context Long memberId) {
        if (memberId == null) return null;
        return playerService.getPlayer(contestId, memberId).orElse(null);
    }

}
