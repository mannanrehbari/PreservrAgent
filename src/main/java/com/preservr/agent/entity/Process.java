package com.preservr.agent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
@Slf4j
public class Process {

    private String pid;
    private String command;
    private Long cpuPercent;
    private String cpuTime;
    private String memory;
    private String ppid;
    private String user;

}
