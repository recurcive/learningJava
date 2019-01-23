package countdownlatch;

import java.util.concurrent.CountDownLatch;

public class Camels {
    private static final String[] messages = {
            "Верблюд 1 дошел",
            "Верблюд 2 устал",
            "Верблюд 3 упал",
            "Верблюд 4 издох",
            "Верблюд 5 устал",
            "Верблюд 6 дошел",
            "Верблюд 7 издох",
            "Верблюд 8 упал",
    };

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch counter = new CountDownLatch(messages.length);
        for (int i = 0; i < messages.length; i++) {
            final int num = i;
            new Thread(() -> {
                System.out.println(messages[num]);
                counter.countDown();
            }).start();
        }
        counter.await();
        System.out.println("Верблюды кончились");
    }
}
