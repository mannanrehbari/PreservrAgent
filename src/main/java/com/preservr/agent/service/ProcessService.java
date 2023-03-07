package com.preservr.agent.service;

import com.preservr.agent.entity.Process;
import com.preservr.agent.entity.Snapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProcessService {

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
        usageDataMap.put("key", "Valueee");
        return usageDataMap;
    }

    private List<Process> createListOfProcesses(List<String> processList) {
        List<Process> processes = new ArrayList<>();
        Process p1 = new Process();
        p1.setPid("Fake");
        p1.setUser("not fake");
        processes.add(p1);
        return processes;
    }

}
