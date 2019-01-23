package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinTest {
	private static final int POOLS_NUM = Runtime.getRuntime().availableProcessors();
	
	@SuppressWarnings("serial")
	private static class MyProcess extends RecursiveAction {
		final int number;

		public MyProcess(int n) { number = n; }

		@Override
		protected void compute() {
			for (int i = 0; i < 3; i++) {
				int p = 0;
				for (int k = 0; k < 10000000; k++) p+=k;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ForkJoinPool executor = new ForkJoinPool();

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
