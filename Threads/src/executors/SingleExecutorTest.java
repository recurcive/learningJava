package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleExecutorTest {
    final static Runnable process = new Runnable() {
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(process);
        executor.execute(process);
        executor.execute(process);
        executor.shutdown();

        System.out.println("Все процессы созданы и запущены");
    }
}
