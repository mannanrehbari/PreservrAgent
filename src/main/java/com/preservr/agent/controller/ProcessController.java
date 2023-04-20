package com.preservr.agent.controller;

import com.preservr.agent.entity.TerminationResponse;
import com.preservr.agent.service.ProcessService;
import com.preservr.agent.task.TopInfoTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
                while (EMIT) {
                    sseEmitter.send(SseEmitter.event()
                            .name("processCpuUsage")
                            .data(TopInfoTask.SNAPSHOT));
                    // wait for a few seconds before sending next event
                    Thread.sleep(5000);
                }
                sseEmitter.complete();
            } catch (Exception ex) {
                sseEmitter.completeWithError(ex);
            }
        });
        return sseEmitter;
    }

    @GetMapping("/process/terminate/{pid}")
    public ResponseEntity<TerminationResponse> terminateProcessByPid(@PathVariable String pid) {
        String terminationStatus = processService.terminateProcessByPid(pid);
        boolean success = terminationStatus.equals(ProcessService.PROCESS_TERMINATED);
        TerminationResponse response = new TerminationResponse(success, terminationStatus);
        return ResponseEntity.ok(response);
//        return processService.terminateProcessByPid(pid);
    }

}
