package com.preservr.agent.controller;

import com.preservr.agent.entity.OSInfo;
import com.preservr.agent.service.OSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OSController {

    private OSService osService;

    public OSController(OSService osService) {
        this.osService = osService;
    }

    @GetMapping("/system/info")
    public OSInfo getSystemInformation() {
        return osService.getPlatformInformation();
    }


}
