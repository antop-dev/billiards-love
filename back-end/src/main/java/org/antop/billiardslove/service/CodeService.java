package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dao.CodeDao;
import org.antop.billiardslove.dto.CodeDto;
import org.antop.billiardslove.mapper.CodeMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 코드 그룹 & 코드 관련 서비스
 *
 * @author antop
 */
@RequiredArgsConstructor
@Service
public class CodeService {
    private final CodeDao codeDao;
    private final CodeMapper codeMapper;

    public Optional<CodeDto> getCode(String group, String code) {
        return codeDao.findById(group, code).map(codeMapper::toDto);
    }

}
