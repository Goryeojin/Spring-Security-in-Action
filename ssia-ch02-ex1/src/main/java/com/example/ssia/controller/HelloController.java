package com.example.ssia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨텍스트에 빈 등록. HTTP 응답의 본문에 반환되는 값을 지정해야 함.
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        // 인증을 위한 올바른 자격 증명을 제공하지 않으면 "HTTP 401 Unauthorized"가 반환된다고 하는데,
        // 상태 코드 "302 FOUND" 가 반환되었다. 시큐리티 버전 문제로 상태 코드를 302에서 401로 변경하여 반환하는 것이 좋다.
        return "Hello!";
    }
}
