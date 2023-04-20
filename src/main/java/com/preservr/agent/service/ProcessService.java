package com.preservr.agent.service;

import com.preservr.agent.entity.Process;
import com.preservr.agent.entity.Snapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProcessService {

    private static String PROCESS_USER_ERROR = "PROCESS NOT INITIATED BY USER";
    public static String PROCESS_TERMINATED = "PROCESS TERMINATED";
    private static String PROCESS_TERMINATION_ERROR = "ERROR TERMINATING PROCESS";
    private static String PROCESS_NOT_FOUND = "PROCESS NOT FOUND";

    public String terminateProcessByPid(String pid) {
        long pidL = Long.parseLong(pid);
        Optional<ProcessHandle> processHandleOptional = ProcessHandle.of(pidL);
        if(processHandleOptional.isPresent()) {
            ProcessHandle processHandle = processHandleOptional.get();

            // check if it is a userprocess
            String osUsername = System.getProperty("user.name");
            String processUsername = processHandle.info().user().get();
            if(!osUsername.equals(processUsername)) {
                log.error("Process not initiated by the user. ");
                return PROCESS_USER_ERROR;
            }
            boolean isTerminated = processHandle.destroy();
            if(!isTerminated) {
                isTerminated = processHandle.destroyForcibly();
            }
            if(isTerminated) {
                log.info("Process with PID " + pidL + " terminated!");
                return PROCESS_TERMINATED;
            } else {
                log.warn("Error terminating process with PID " + pidL);
                return PROCESS_TERMINATION_ERROR;
            }
        } else {
            log.error("Process with pid - " + pidL + " not found." );
            return PROCESS_NOT_FOUND;
        }
    }



    public Snapshot mapLinesToSnapshot(List<String> topSnapshot) {
        Snapshot snapshot = new Snapshot();
        List<String> usageSublist = topSnapshot.subList(0, 10);
        List<String> processSublist = topSnapshot.subList(10, topSnapshot.size());
        Map<String, String> usageDataMap = createUsageDataMap(usageSublist);
        List<Process> processes = createListOfProcesses(processSublist);
        snapshot.setProcesses(processes);
        snapshot.setUsageData(usageDataMap);
        return snapshot;
    }

    private Map<String, String> createUsageDataMap(List<String> usageData) {
        Map<String, String> usageDataMap = new HashMap<>();
        int count = 0;
        for(String item: usageData) {
            if(count == 1 ) {
                usageDataMap.put("SnapshotTime", item);
            } else {
                String [] parts = item.split(":", 2);
                usageDataMap.put(parts[0], parts[1].trim());
            }
            count++;
        }
        return usageDataMap;
    }

    private List<Process> createListOfProcesses(List<String> snapshotList) {
        List<Process> processes = new ArrayList<>();
        String headers = snapshotList.get(1);

        int pid_start = 0;
        int pid_end = headers.indexOf("COMMAND");

        int command_start = pid_end;
        int command_end = headers.indexOf("%CPU");

        int cpu_start = command_end;
        int cpu_end = headers.indexOf("TIME");

        int time_start = cpu_end;
        int time_end = headers.indexOf("#TH");

        int mem_start = headers.indexOf("MEM");
        int mem_end = headers.indexOf("PURG");

        int ppid_start = headers.indexOf("PPID");
        int ppid_end = headers.indexOf("STATE");

        int user_start = headers.indexOf("USER");
        int user_end = headers.indexOf("#MREGS");

        snapshotList.subList(0, 2).clear();
        for (String snapshotLine: snapshotList) {
            Process process = getProcessFromSnapshotLine(pid_start, pid_end,
                    command_start, command_end,
                    cpu_start, cpu_end,
                    time_start, time_end,
                    mem_start, mem_end,
                    ppid_start, ppid_end,
                    user_start, user_end,
                    snapshotLine);
            processes.add(process);
        }
        return processes;
    }

    private Process getProcessFromSnapshotLine(
            int pid_start, int pid_end,
            int command_start, int command_end,
            int cpu_start, int cpu_end,
            int time_start, int time_end,
            int mem_start, int mem_end,
            int ppid_start, int ppid_end,
            int user_start, int user_end,
            String snapshotLine) {

        Process process = new Process();
        process.setPid(snapshotLine.substring(pid_start, pid_end).trim());
        process.setCommand(snapshotLine.substring(command_start, command_end).trim());
        process.setCpuPercent(snapshotLine.substring(cpu_start, cpu_end).trim());
        process.setCpuTime(snapshotLine.substring(time_start, time_end).trim());
        process.setMemory(snapshotLine.substring(mem_start, mem_end).trim());
        process.setPpid(snapshotLine.substring(ppid_start, ppid_end).trim());
        process.setUser(snapshotLine.substring(user_start, user_end).trim());
        return process;

    }


}
