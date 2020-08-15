package org.antop.billiardslove;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("production")
class BilliardsLoveApplicationTests {

    @Test
    void main() {
        // https://stackoverflow.com/questions/46650268/how-to-test-main-class-of-spring-boot-application
        BilliardsLoveApplication.main(new String[] {});
    }

}
