package org.antop.billiardslove.api;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Profile({Profiles.LOCAL, Profiles.TEST})
public class JwtValidationApi {

    @GetMapping("/jwt")
    public Long test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            log.warn("authentication is null!");
            return 0L;
        }

        log.info("name = {}, principal = {}, details = {}, credentials = {}, authorities = {}",
                auth.getName(), auth.getPrincipal(), auth.getDetails(), auth.getCredentials(), auth.getAuthorities());

        return Long.parseLong(auth.getName());
    }
}
