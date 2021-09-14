package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.Outcome;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public abstract class MatchMapper {
    private static final String MAPPING_OPPONENT = "opponent";
    private static final String MAPPING_MATCH_RESULT = "matchResult";

    @Autowired
    private PlayerMapper playerMapper;

    @Mapping(source = "match", target = "opponent", qualifiedByName = MAPPING_OPPONENT)
    @Mapping(source = "match", target = "result", qualifiedByName = MAPPING_MATCH_RESULT)
    @Mapping(source = "confirmed", target = "closed")
    public abstract MatchDto toDto(Match match, @Context long memberId);

    @Named(MAPPING_OPPONENT)
    protected PlayerDto opponent(Match match, @Context long memberId) {
        Player player = match.getOpponent(memberId);
        return playerMapper.toDto(player);
    }

    @Named(MAPPING_MATCH_RESULT)
    protected String[] matchResult(Match match, @Context long memberId) {
        return Arrays.stream(match.getMatchResult(memberId).toArray())
                .map(Outcome::name)
                .toArray(String[]::new);
    }

}
