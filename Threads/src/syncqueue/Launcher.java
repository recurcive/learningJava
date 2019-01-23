package syncqueue;

/**
 * Запускает два независимых процесса, работающих с одной и той же очередью.
 */
public class Launcher {
    public static void main(String[] args) {
        final SyncQueue queue = new SyncQueue();

        Thread t1 = new Thread(
                new Runnable() {
                    public void run() {
                        for (int i = 0; i < 50; ++i) {
                            System.out.println(Thread.currentThread().getName() + ": added " + queue.add());
                            System.out.println(Thread.currentThread().getName() + ": added " + queue.add());
                            System.out.println(Thread.currentThread().getName() + ": extracted " + queue.get());
                            System.out.println(Thread.currentThread().getName() + ": extracted " + queue.get());
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException x) {
                                System.out.println(Thread.currentThread().getName() + ": interrupted!");
                                break;
                            }
                        }
                    }
                }, "Thread 1");

        Thread t2 = new Thread(
                new Runnable() {
                    public void run() {
                        for (int i = 0; i < 70; ++i) {
                            System.out.println(Thread.currentThread().getName() + ": added " + queue.add());
                            System.out.println(Thread.currentThread().getName() + ": extracted " + queue.get());
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException x) {
                                System.out.println(Thread.currentThread().getName() + ": interrupted!");
                                break;
                            }
                        }
                    }
                }, "Thread 2");

        queue.add();
        queue.add();
        queue.add();
        queue.add();
        t1.start();
        t2.start();
    }
}
