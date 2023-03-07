package com.preservr.agent.task;

import com.preservr.agent.entity.Snapshot;
import com.preservr.agent.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TopInfoTask implements Runnable{

    public static Snapshot snapshot;
    private ProcessService processService;

    @Autowired
    public TopInfoTask(ProcessService processService) {
        this.processService = processService;
    }

    @Override
    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "top -o cpu -O +rsize -s 5 -n 30");
        try {
            java.lang.Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            int lineCount = 0;
            List<String> snapshotLines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                lineCount++;
//                System.out.println( lineCount + " " + line);
                snapshotLines.add(line);
                if(lineCount%42==0) {
                    //process the snapshot strings
                    snapshot = processService.mapLinesToSnapshot(snapshotLines);
                    // clear the list
                    snapshotLines.clear();
                    lineCount =0;
                }
            }

        } catch (Exception e) {
            log.error("Exception Occurred");
        }
    }
}
