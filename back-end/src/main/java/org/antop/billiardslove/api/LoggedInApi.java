package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoggedInApi {

    @PostMapping("/api/v1/logged-in")
    public LoggedInResponse loggedIn(@RequestBody LoggedInRequest request) {
        log.debug("{}", request);
        log.debug("LocalDateTime = {}", request.getConnectedAt());
        return LoggedInResponse.builder().token("token").build();
    }

}
