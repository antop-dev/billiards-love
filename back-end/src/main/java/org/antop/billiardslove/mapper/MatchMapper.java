package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.MapStruct;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.MatchPlayerDto;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.MatchResult;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MapStruct.COMPONENT_MODEL)
public abstract class MatchMapper {
    private static final String MAPPING_LEFT = "left";
    private static final String MAPPING_RIGHT = "right";

    @Autowired
    private MatchPlayerMapper matchPlayerMapper;

    @Mapping(source = "match", target = "left", qualifiedByName = MAPPING_LEFT)
    @Mapping(source = "match", target = "right", qualifiedByName = MAPPING_RIGHT)
    @Mapping(source = "confirmed", target = "closed")
    public abstract MatchDto toDto(Match match, @Context Member member);

    @Named(MAPPING_LEFT)
    protected MatchPlayerDto left(Match match, @Context Member member) {
        Player player = member.isManager() ? match.getPlayer1() : match.getMe(member.getId());
        MatchResult result = member.isManager() ? match.getMatchResult1() : match.getMyResult(member.getId());
        return matchPlayerMapper.toDto(player, result);
    }

    @Named(MAPPING_RIGHT)
    protected MatchPlayerDto matchResult(Match match, @Context Member member) {
        Player player = member.isManager() ? match.getPlayer2() : match.getOpponent(member.getId());
        MatchResult result = member.isManager() ? match.getMatchResult2() : match.getOpponentResult(member.getId());
        return matchPlayerMapper.toDto(player, result);
    }

}
