package com.preservr.agent.service;

import com.preservr.agent.entity.OSInfo;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Service
public class OSService {

    public OSInfo getPlatformInformation(){
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        OSInfo osInfo = new OSInfo(
                osMXBean.getArch(),
                osMXBean.getName(),
                osMXBean.getAvailableProcessors(),
                osMXBean.getVersion()
        );
        return osInfo;
    }
}
