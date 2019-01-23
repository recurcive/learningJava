package executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FutureExecutorTest {
    private static class Summator implements Callable<Integer> {
        private final int limit;

        public Summator(int limit) { this.limit = limit; }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int j = 0; j < 10000; ++j) {
                sum = 0;
                for (int i = 1; i <= limit; ++i) {
                    sum += i;
                }
            }
            return sum;
        }

    }

    private static void printTaskResult(final Future<?> task) {
        (new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println(task.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService executor = Executors.newCachedThreadPool();
        final Future<Integer> task1 = executor.submit(new Summator(10000));
        final Future<Integer> task2 = executor.submit(new Summator(1000));
        final Future<Integer> task3 = executor.submit(new Summator(100));

        printTaskResult(task1);
        printTaskResult(task2);
        printTaskResult(task3);

        executor.shutdown();
    }

}
