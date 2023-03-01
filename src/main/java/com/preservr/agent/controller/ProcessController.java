package com.preservr.agent.controller;

import com.preservr.agent.entity.Process;
import com.preservr.agent.service.ProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProcessController {

    private ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping("/process/all")
    public List<Process> getProcessInformation(){
        return processService.getAllProcesses();
    }

    @GetMapping("/process/user")
    public List<Process> getUserProcesses(){
        return processService.getUserProcesses();
    }


}
