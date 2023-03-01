package com.preservr.agent.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class Process {

    private List<Process> children;
    private List<Process> descendants;
    private Info info;
    private Process parent;
    private long pid;

}
