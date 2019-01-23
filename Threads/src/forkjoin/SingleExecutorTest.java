package forkjoin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Запускаем процессы с помощью простого исполнителя задач.
 * Этот исполнитель поддерживает только последовательное исполнение задач,
 * поэтому процессы запускаются по очереди. 
 */
public class SingleExecutorTest {
	/**
	 * Имитирует выполнение полезной работы.
	 * Печатает отчет о выполнении в виде трех сообщений.
	 */
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
		// Создаем исполнителя.
		ExecutorService executor = Executors.newSingleThreadExecutor();

		long startTime = System.currentTimeMillis();
		// Запускаем все работы.
		for (int i = 0; i < 25; i++) {
			executor.execute(new MyProcess(i));
		}
		System.out.println("Все процессы созданы и запущены");

		// Исполнитель завершает выполнение задач.
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
		System.out.format("Выполнение всех задач закончено за %d миллисекунд\n",
				(int)(System.currentTimeMillis() - startTime));
	}
}
