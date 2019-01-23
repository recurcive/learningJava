package Semaphores;

import java.util.concurrent.Semaphore;

/**
 * Реализация задачи о производителе и потребителе.
 * Это решение не приводит к тупиковой ситуации, оно использует механизм 
 * объектов высокого уровня {@link java.util.concurrent.Semaphore}.
 */
public class Buffer {
	final int MAX_COUNT = 10;
	// Буфер на 10 целых чисел.
	final private Integer[] buffer = new Integer[MAX_COUNT];
	// Указывает на первую занятую ячейку.
	private int first = 0;
	// Указывает на количество занятых ячеек.
	private int count = 0;

	// Счетчик свободных ячеек
	final Semaphore free = new Semaphore(MAX_COUNT);
	// Счетчик занятых ячеек
	final Semaphore busy = new Semaphore(0);


	/**
	 * Записывает число в буфер.
	 * @param n Записываемое число.
	 * @throws InterruptedException 
	 */
	public void addObject(Integer n) {
		// Запрашиваем ресурс - свободную ячейку.
		// Если нет такой - ждем, пока не освободится.
		free.acquireUninterruptibly();
		// Блокируем разделяемый ресурс - буфер
		synchronized(this) {
			// Кладем в буфер заданное значение
			buffer[(first + count++) % MAX_COUNT] = n;
			// Освобождаем ресурс занятых ячеек
			busy.release();
		}
	}

	/**
	 * Чтение числа из буфера с удалением.
	 * @return Прочитанное число.
	 * @throws InterruptedException 
	 */
	public Integer removeObject() {
		Integer element;
		// Запрашиваем ресурс - занятую ячейку.
		// Если нет такой - ждем, пока не освободится.
		busy.acquireUninterruptibly();
		// Блокируем разделяемый ресурс - буфер
		synchronized(this) {
			// Выбираем значение из буфера
			element = buffer[first];
			first = (first+1) % MAX_COUNT;
			count--;
			// Освобождаем ресурс свободных ячеек
			free.release();
			return element;
		}
	}

	/**
	 * Тестовая функция создает два потока - читателя и писателя - и запускает их.
	 * @param args Не используется.
	 */
	public static void main(String[] args) {
		final Buffer buffer = new Buffer();

		final Reader reader = new Reader(buffer);
		final Writer writer = new Writer(buffer);

		reader.start();
		writer.start();
	}
}
