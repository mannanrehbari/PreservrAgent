package com.preservr.agent.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@NoArgsConstructor
@ToString
@Setter @Getter
public class Info {
    private Optional<String []> arguments;
    private Optional<String> command;
    private Optional<String> commandLine;
    private Optional<Instant> startInstant;
    private Optional<Duration> totalCpuDuration;
    private Optional<String> user;
}
