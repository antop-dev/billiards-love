package org.antop.billiardslove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

/**
 * {@link MockMvc} 기반 테스트
 *
 * @author antop
 */
@AutoConfigureMockMvc
abstract public class MockMvcBase extends SpringBootBase {
    @Autowired
    protected MockMvc mockMvc;

}
