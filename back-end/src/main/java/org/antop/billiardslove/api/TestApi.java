package org.antop.billiardslove.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping("/jwt")
    public String test() {

        Authentication member = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("멤버 id = " + member.getPrincipal() + " 멤버 token = " + member.getCredentials());

        return "ok";
    }
}
