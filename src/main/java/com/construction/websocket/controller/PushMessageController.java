package com.construction.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@Controller
public class PushMessageController {

    @SendTo("topic/subconstructor/fingerprint")
    @MessageMapping("subconstructor/fingerprint")
    public String pushResult(String body) {
        log.info("body is:" + body);
        return "body is:" + body + ", at:" + LocalDateTime.now();
    }
}
