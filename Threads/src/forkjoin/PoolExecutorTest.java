package forkjoin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Запускаем процессы с помощью исполнителя задач с несколькими потоками.
 * Этот исполнитель поддерживает только параллельное исполнение нескольких задач,
 * поэтому процессы запускаются одновременно, но не более трех. 
 */
public class PoolExecutorTest {
	private static final int POOLS_NUM = Runtime.getRuntime().availableProcessors();
	
	private static class MyProcess implements Runnable {
		final int number;
		
		public MyProcess(int n) { number = n; }
		
	    public void run() {
	      for (int i = 0; i < 3; i++) {
	    	  int p = 0;
	    	  for (int k = 0; k < 10000000; k++) p+=k;
	      }
	    }
	}
  
  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(POOLS_NUM);
    
    System.out.format("Всего имееется %d процессоров\n", POOLS_NUM);
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 25; i++) {
    	executor.execute(new MyProcess(i));
    }
    System.out.println("Все процессы созданы и запущены");
    
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);
	System.out.format("Выполнение всех задач закончено за %d миллисекунд\n",
			(int)(System.currentTimeMillis() - startTime));
  }
}
