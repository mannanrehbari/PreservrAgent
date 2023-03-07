package com.preservr.agent.service;

import com.preservr.agent.entity.OSInformation;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Service
public class OSService {

    public OSInformation getPlatformInformation(){
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        OSInformation osInfo = new OSInformation(
                osMXBean.getArch(),
                osMXBean.getName(),
                osMXBean.getAvailableProcessors(),
                osMXBean.getVersion()
        );
        return osInfo;
    }
}
