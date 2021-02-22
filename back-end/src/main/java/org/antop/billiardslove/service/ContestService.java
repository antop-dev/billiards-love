package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.ContestRank;

import java.util.List;

public interface ContestService {
    ContestDto getContest(long id);

    List<ContestDto> getAllContests();

    List<ContestRank> getRanks(long id);
}
