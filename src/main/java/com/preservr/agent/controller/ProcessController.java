package com.preservr.agent.controller;

import com.preservr.agent.service.ProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.util.concurrent.Executors;

@RestController
public class ProcessController {

    private ProcessService processService;

    public static boolean EMIT;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping("/process/top")
    public SseEmitter runTopCommand() {
        SseEmitter sseEmitter = new SseEmitter(-1L);
        EMIT = true;
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while(EMIT) {
                    sseEmitter.send(SseEmitter.event().name("message").data(new Timestamp(System.currentTimeMillis())));
                    // wait for a few seconds
                    Thread.sleep(4000);
                }
                // complete the emitter
                sseEmitter.complete();
            } catch (Exception ex) {
                sseEmitter.completeWithError(ex);
            }
        });
        return sseEmitter;

    }


}
