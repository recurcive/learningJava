package executors;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledExecutorTest {
    private static final long start = (Calendar.getInstance()).getTimeInMillis();

    private static class Printer implements Runnable {
        final String name;

        public Printer(String s) { name = s; }

        public void run() {
            System.out.format("%s: at %4.1f\n", name, ((Calendar.getInstance()).getTimeInMillis() - start)/1000.0);
        }
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        final String s1 = "Thread 1";
        final String s2 = "Thread 2";
        final String s3 = "Thread 3";
        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleWithFixedDelay(new Printer(s1), 3000, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(new Printer(s2), 2000, 700, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(new Printer(s3), 2500, 600, TimeUnit.MILLISECONDS);

        Thread.sleep(8100);
        executor.shutdown();
    }

}
