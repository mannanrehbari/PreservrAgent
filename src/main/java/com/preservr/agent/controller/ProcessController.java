package com.preservr.agent.controller;

import com.preservr.agent.service.ProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    private ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping("/process/top")
    public void runTopCommand() {
        processService.topByCPUUsage();
    }


}
