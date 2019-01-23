package blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueWorker {
    private static int COUNT = 100;

    public static void main(String[] args) {
        final BlockingQueue<Long> bq = new LinkedBlockingQueue<>(5);

        Thread writer = new Thread(new Runnable() {
            final long start = System.currentTimeMillis();
            final Random rand = new Random();

            @Override
            public void run() {
                for (int i = 0; i < COUNT; i++) {
                    try {
                        Thread.sleep(rand.nextInt(30));
                        bq.put(System.currentTimeMillis() - start);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread reader = new Thread(() -> {
            try {
                for (int i = 0; i < COUNT; i++) {
                    if (i % 20 == 0) System.out.println();
                    System.out.print(String.format("%6d", bq.take()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        });
        writer.start();
        reader.start();
    }
}
