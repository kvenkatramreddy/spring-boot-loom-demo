package com.example.loom;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.Executors.newCachedThreadPool;

@SpringBootApplication
public class LoomDemoApplication {

	public static void main(String[] args) {
       // System.setProperty("jdk.defaultScheduler.parallelism","1000");
		SpringApplication.run(LoomDemoApplication.class, args);
	}

}
