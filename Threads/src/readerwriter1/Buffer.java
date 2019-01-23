package readerwriter1;

/**
 * Реализация задачи о производителе и потребителе.
 * Это решение почти всегда приводит к тупиковой ситуации.
 */
public class Buffer {
    final int MAX_COUNT = 10;
    // Буфер на 10 целых чисел.
    private Integer[] buffer = new Integer[MAX_COUNT];
    // Указывает на первую занятую ячейку.
    private int first = 0;
    // Число занятых ячеек.
    private int count = 0;

    /**
     * Записывает число в буфер.
     * @param n Записываемое число.
     */
    public synchronized void addObject(Integer n) {
        while (true) {
            if (count == MAX_COUNT) {
                // Записывать некуда. Ждем, пока не освободится ячейка.
                // Однако, метод синхронизирован, поэтому буфер блокирован! Это тупик.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException x) {
                }
            } else {
                // Записываем число в буфер.
                // Если читатель ждет этого числа, то он его должен увидеть, когда проснется.
                buffer[(first + count++) % MAX_COUNT] = n;
                break;
            }
        }
    }

    /**
     * Чтение числа из буфера с удалением.
     * @return Прочитанное число.
     */
    public synchronized Integer removeObject() {
        while (true) {
            if (count == 0) {
                // Буфер пуст, Ждем, пока в нем не появится хотя бы одно значение.
                // Однако, метод синхронизирован, поэтому буфер блокирован! Это тупик.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException x) {
                }
            } else {
                // Читаем число из буфера и освобождаем ячейку.
                // Если писатель ждет этого места, то он его должен увидеть, когда проснется.
                Integer toRemove = buffer[first++];
                first %= MAX_COUNT;
                count--;
                return toRemove;
            }
        }
    }

    /**
     * Тестовая функция создает два потока - читателя и писателя - и запускает их.
     * @param args Не используется.
     */
    public static void main (String[] args) {
        final Buffer buffer = new Buffer();

        Reader reader = new Reader(buffer);
        Writer writer = new Writer(buffer);

        reader.setWriterThread(writer);
        writer.setReaderThread(reader);

        reader.start();
        writer.start();
    }
}
