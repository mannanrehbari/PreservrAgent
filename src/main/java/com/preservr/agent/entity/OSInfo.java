package com.preservr.agent.entity;

import lombok.*;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@ToString
public class OSInfo {
    private String osArch;
    private String osName;
    private Integer availableProcessors;
    private String osVersion;
}
