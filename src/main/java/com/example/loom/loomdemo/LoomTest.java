package com.example.loom.loomdemo;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class LoomTest {

    public static void main(String[] args) {
        //vt();

       // cf();

        HttpClient client = HttpClient.newBuilder().
            executor(Executors.newUnboundedVirtualThreadExecutor()).build();
       // client.sendAsync()

    }

    private static void cf(){
        try (var e = Executors.newUnboundedVirtualThreadExecutor()) {
            List<CompletableFuture<String>> tasks = e.submitTasks(List.of(
                () -> "a",
                () -> { throw new IOException("too lazy for work"); },
                () -> "b"
                ));

            try {
                String first = CompletableFuture.stream(tasks)
                    .filter(Predicate.not(CompletableFuture::isCompletedExceptionally))
                    .map(CompletableFuture::join)
                    .findFirst()
                    .orElse(null);
                System.out.println("one result: " + first);
            } catch (Exception ee) {
                System.out.println("¯\\_(ツ)_/¯");
            } finally {
                tasks.forEach(cf -> cf.cancel(true));
            }
        }
    }

    private static void vt() {
        Thread.startVirtualThread(() -> {
            System.out.println("Hello, Loom!");
        });

        ThreadFactory tf = Thread.builder().virtual().factory();
        ExecutorService e = Executors.newUnboundedExecutor(tf);
        Future<String> submit = e.submit(() -> {
            System.out.println("Started");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                throw new RuntimeException(interruptedException);
               // return "failed";
            }
            System.out.println("finished");
            return "test";
        });
        submit.cancel(true);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        System.out.println("testing");
    }

    private static void sleep() {

    }
}
