package com.construction.basic.health.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthController {

    private final HttpServletRequest servletRequest;

    public HealthController(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @GetMapping
    public String sayOk() {
        log.info("ip is:" + servletRequest.getRemoteAddr());
        return "ok";
    }

    @GetMapping("/isloggedin")
    public boolean isLoggedIn() {
        return true;
    }
}
