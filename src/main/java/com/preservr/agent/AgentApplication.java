package com.preservr.agent;

import com.preservr.agent.task.TopInfoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
public class AgentApplication {

	@Autowired
	TopInfoTask topInfoTask;

	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startTopInfoTask() {
		TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.execute(topInfoTask);
	}

}
