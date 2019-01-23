package mutualcounters;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Взаимная блокировка процессов преодолена
 * с помощью условной постановки замка.
 */
public class CounterLock {
    int counter = 0;
    final Lock lock = new ReentrantLock();

    public void change(CounterLock other) {
        boolean myLock = false, otherLock = false;
        try {
            myLock = lock.tryLock();
            otherLock = other.lock.tryLock();
            if (myLock && otherLock) {
                counter++;
                other.makeChange();
            }
        } finally {
            if (myLock) lock.unlock();
            if (otherLock) other.lock.unlock();
        }
    }

    public void makeChange() {
        counter += 2;
    }

    public static void main(String[] args) {
        final CounterLock c1 = new CounterLock();
        final CounterLock c2 = new CounterLock();
        Thread t1 = new Thread(
                new Runnable() {
                    public void run() {
                        for (int i = 0; i < 1000; ++i) {
                            c1.change(c2);
                        }
                        System.out.println(Thread.currentThread().getName() + " done");
                        System.out.println("c1 counter = " + c1.counter + "; c2 counter = " + c2.counter);
                    }
                });
        Thread t2 = new Thread(
                new Runnable() {
                    public void run() {
                        for (int i = 0; i < 1000; ++i) {
                            c2.change(c1);
                        }
                        System.out.println(Thread.currentThread().getName() + " done");
                        System.out.println("c2 counter = " + c2.counter + "; c1 counter = " + c1.counter);
                    }
                });
        t1.start();
        t2.start();
    }
}
