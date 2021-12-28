package multi_thread;

import java.util.concurrent.*;

public class ExecutorTest {

    /**
     * One point to note here is that Executor does not strictly
     *  require the task execution to be asynchronous. In the simplest case,
     *  an executor can invoke the submitted task instantly in the invoking thread.
     */
    public static void test_executor() {
        Executor executor = new Invoker();
        executor.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("tasks...");
        });
        System.out.println("outside the task...");
    }

    /**
     * ExecutorService is a complete solution for asynchronous processing.
     *  It manages an in-memory queue
     *  and schedules submitted tasks based on thread availability.
     */
    public static void test_ExecutorService() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Task());
        Thread.sleep(3000);
        executorService.shutdown();
    }

    /**
     * ScheduledExecutorService is a similar interface to ExecutorService,
     * but it can perform tasks periodically.
     *
     * Executor and ExecutorService‘s methods are scheduled on the spot
     *  without introducing any artificial delay. Zero or any negative value
     *  signifies that the request needs to be executed instantly.
     */
    public static void test_ScheduledExecutorService() throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService =
                Executors.newSingleThreadScheduledExecutor();
        Future<String> future = executorService.schedule(() -> {
            Thread.sleep(1000);
            return "Hello world";
        }, 1, TimeUnit.SECONDS);
        System.out.println(future.get());
        System.out.println("outside scheduled executor...");
        executorService.shutdown();
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        test_executor();

        test_ExecutorService();

        test_ScheduledExecutorService();
    }

}

class Invoker implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in tasks ...");
    }
}