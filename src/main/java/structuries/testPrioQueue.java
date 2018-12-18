package structuries;

import java.util.PriorityQueue;
import java.util.Random;

public class testPrioQueue {
    private static Random rnd = new Random();
    public static void main(String[] args) {
        PriorityQueue<String> pq = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            pq.offer("Str " + rnd.nextInt(100));
        }
        for (String s : pq) {
            System.out.println(s);
        }
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
