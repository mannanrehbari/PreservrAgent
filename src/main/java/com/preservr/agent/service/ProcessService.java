package com.preservr.agent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProcessService {

    public List<ProcessHandle> getAllProcesses() {
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        List<ProcessHandle> processes =
                liveProcesses.filter(ProcessHandle::isAlive).collect(Collectors.toList());
        return null;
    }

}
