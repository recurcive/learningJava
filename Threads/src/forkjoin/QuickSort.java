package forkjoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Быстрая сортировка с помощью ForkJoin работ.
 * 
 * @param <T> тип элементов сортируемого массива
 */
public class QuickSort<T extends Comparable<T>> extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	private static final int MIN_PARALLEL = 1000; 
	
	final private T[] array;		// Сортируемый массив
	final private int low, high;	// Верхняя и нижняя границы сортируемого участка
	
	/**
	 * Конструктор работы.
	 * 
	 * @param array
	 * @param from
	 * @param to
	 */
	public QuickSort(T[] array, int from, int to) {
		this.array = array;
		low = from;
		high = to;
	}
	
	@Override
	protected void compute() {
		int bottom = low;
		int top = high;
		if (top - bottom <= MIN_PARALLEL) {
			Arrays.sort(array, low, high);
			return;
		}
		
		// Первый элемент, который будем ставить на "свое" место.
		T element = array[bottom];
		while (top > bottom) {
			while (bottom < top && array[--top].compareTo(element) >= 0) ;
			array[bottom] = array[top];
			while (bottom < top && array[++bottom].compareTo(element) <= 0) ;
			array[top] = array[bottom];
		}
		
		// Установка элемента на место.
		array[bottom] = element;
		
		// Рекурсивные вызовы в виде отдельных работ
		new QuickSort<T>(array, low, bottom).fork();
		new QuickSort<T>(array, bottom+1, high).fork();
	}
	
	/**
	 * Проверка того, что массив упорядочен по возрастанию.
	 * 
	 * @param array	Проверяемый массив
	 * @return		true, если массив упорядочен, false в противном случае
	 */
	private static <T extends Comparable<T>> boolean test(T[] array) {
		for (int i = 0; i < array.length - 1; ++i) {
			if (array[i].compareTo(array[i+1]) > 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws InterruptedException {
		// Создаем массив из случайных элементов
		final int COUNT = 1000000;
		
		Random rnd = new Random();
		Integer[] array = new Integer[COUNT];
		for (int i = 0; i < COUNT; i++) array[i] = rnd.nextInt(2*COUNT);
		
		// Запускаем "параллельную" версию сортировки с помощью пула Fork/Join работ
		ForkJoinPool pool = new ForkJoinPool();
		
		System.out.println("Параллельная сортировка");
		long start = System.currentTimeMillis();
		pool.execute(new QuickSort<Integer>(array, 0, COUNT));
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.MINUTES);
		long elapsed = System.currentTimeMillis() - start;
		
		System.out.format("Сортировка выполнена %s за %d миллисекунд\n",
				test(array) ? "успешно" : "с ошибками", elapsed);
		
		for (int i = 0; i < COUNT; i++) array[i] = rnd.nextInt(2*COUNT);
		System.out.println("Последовательная сортировка");
		start = System.currentTimeMillis();
		Arrays.sort(array);
		elapsed = System.currentTimeMillis() - start;
		
		System.out.format("Сортировка выполнена %s за %d миллисекунд\n",
				test(array) ? "успешно" : "с ошибками", elapsed);
	}
}
