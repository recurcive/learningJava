/**
 * Взаимная блокировка процессов
 */
public class CounterBlock {
    int counter = 0;

    public synchronized void change(CounterBlock other) {
        counter++;
        if (other != null) other.change(null);
    }

    public static void main(String[] args) {
        final CounterBlock c1 = new CounterBlock();
        final CounterBlock c2 = new CounterBlock();
        Thread t1 = new Thread(
                () -> {
                        for (int i = 0; i < 1000; ++i) {
                            c1.change(c2);
                        }
                        System.out.println(Thread.currentThread().getName() + " done");
                });
        Thread t2 = new Thread(
                () -> {
                        for (int i = 0; i < 1000; ++i) {
                            c2.change(c1);
                        }
                        System.out.println(Thread.currentThread().getName() + " done");
                });
        t1.start();
        t2.start();
    }
}
