package com.preservr.agent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter @Setter
@Slf4j
public class Snapshot {

    private Map<String, String> usageData;
    private List<Process> processes;

    public Snapshot() {
        usageData = new HashMap<>();
        processes = new ArrayList<>();
    }

}
