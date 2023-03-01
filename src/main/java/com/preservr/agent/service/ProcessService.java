package com.preservr.agent.service;

import com.preservr.agent.entity.Info;
import com.preservr.agent.entity.Process;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProcessService {


    public List<Process> getAllProcesses() {
        List<Process> processes = new ArrayList<>();
        ProcessHandle.allProcesses().forEach(process -> {
            Process process1 = mapToProcessObject(process);
            processes.add(process1);
        });
        return processes;
    }

    public List<Process> getUserProcesses() {
        List<Process> allProcesses = getAllProcesses();
        String currentUser = System.getProperty("user.name");
        System.out.println(currentUser);
        List<Process> userProcesses = allProcesses.stream()
                .filter((process) ->
                        currentUser.equals(process.getInfo().getUser().get())
                )
                .collect(Collectors.toList());
        return userProcesses;
    }


    private Process mapToProcessObject(ProcessHandle process) {
        Process process1 = new Process();
        mapProcessHandleToProcess(process, process1);
        return process1;
    }

    private void mapProcessHandleToProcess(ProcessHandle process, Process process1) {
        Info info = new Info();
//        info.setArguments(process.info().arguments());
//        info.setCommand(process.info().command());
//        info.setCommandLine(process.info().commandLine());
        info.setStartInstant(process.info().startInstant());
        info.setUser(process.info().user());
        info.setTotalCpuDuration(process.info().totalCpuDuration());
        process1.setPid(process.pid());
        process1.setInfo(info);
    }


}
