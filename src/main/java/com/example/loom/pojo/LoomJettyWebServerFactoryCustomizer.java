package com.example.loom.pojo;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.JettyWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

@Component
public class LoomJettyWebServerFactoryCustomizer extends JettyWebServerFactoryCustomizer {


    private final ServerProperties serverProperties;

    @Override
    public int getOrder() {
        return 1;
    }

    public LoomJettyWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
        super(environment, serverProperties);
        this.serverProperties = serverProperties;
    }

    @Override
    public void customize(ConfigurableJettyWebServerFactory factory) {
        super.customize(factory);
        ServerProperties.Jetty jettyProperties = this.serverProperties.getJetty();
        factory.setThreadPool(this.determineThreadPool(jettyProperties.getThreads()));
    }

    private ThreadPool determineThreadPool(ServerProperties.Jetty.Threads properties) {
        BlockingQueue<Runnable> queue = this.determineBlockingQueue(properties.getMaxQueueCapacity());
        int maxThreadCount = properties.getMax() > 0 ? properties.getMax() : 200;
        int minThreadCount = properties.getMin() > 0 ? properties.getMin() : 8;
        int threadIdleTimeout = properties.getIdleTimeout() != null ? (int)properties.getIdleTimeout().toMillis() : '\uea60';
        return new QueuedThreadPool(maxThreadCount, minThreadCount,
            threadIdleTimeout,-1, queue,null,
            Thread.builder().name("virtual").virtual().factory());
    }

    private BlockingQueue<Runnable> determineBlockingQueue(Integer maxQueueCapacity) {
        if (maxQueueCapacity == null) {
            return null;
        } else {
            return (BlockingQueue)(maxQueueCapacity == 0 ? new SynchronousQueue() : new BlockingArrayQueue(maxQueueCapacity));
        }
    }
}
