package com.preservr.agent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class TerminationResponse {
    private boolean success;
    private String message;
}
