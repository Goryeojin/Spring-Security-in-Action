package com.example.ssia.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

//    @GetMapping("/hello")
    public String hello() {
        // SecurityContext 관리 첫 번째 전략 MODE_THREADLOCAL
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();

        return "Hello, " + a.getName() + "!";
    }

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }

    @GetMapping("/bye")
    @Async
    public void goodbye() {
        // 엔드포인트가 비동기가 되면 메서드를 요청하는 스레드와 요청을 수행하는 스레드가 다른 스레드가 된다.
        // 현재 보안 컨텍스트는 MODE_THREADLOCAL 전략 상태로, 메서드가 상위 스레드의 보안 컨텍스트를 상속하지 않는 상태에서 실행되기 때문.
        // => MODE_INHERITABLETHREADLOCAL 전략으로 해결 가능하다.
        // 해당 전략은 프레임워크가 요청의 원래 스레드에 있는 세부 정보를 비동기 메서드의 새로 생성된 스레드로 복사한다.
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
    }
}
