package readerwriter2;


/**
 * Реализация задачи о производителе и потребителе.
 * Это решение не приводит к тупиковой ситуации, но требует, чтобы производитель и потребитель
 * знали друг о друге.
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
    public void addObject(Integer n) {
        while (true) {
            if (count == MAX_COUNT) {
                // Записывать некуда. Ждем, пока не освободится ячейка.
                try {
                    Thread.sleep(20);
                } catch (InterruptedException x) {
                }
            } else {
                // Записываем число в буфер.
                // Если читатель ждет этого числа, то он его должен увидеть, когда проснется.
                synchronized (this) {
                    // Не спим внутри синхронизированного блока!
                    if (count < MAX_COUNT) {
                        buffer[(first + count++) % MAX_COUNT] = n;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Чтение числа из буфера с удалением.
     * @return Прочитанное число.
     */
    public Integer removeObject() {
        while (true) {
            if (count == 0) {
                // Буфер пуст, Ждем, пока в нем не появится хотя бы одно значение.
                try {
                    Thread.sleep(20);
                } catch (InterruptedException x) {
                }
            } else {
                // Читаем очередное число из буфера.
                synchronized (this) {
                    // Перед чтением надо снова проверить, что в буфере есть что-то.
                    if (count > 0) {
                        Integer toRemove = buffer[first++];
                        first %= MAX_COUNT;
                        count--;
                        return toRemove;
                    }
                }
            }
        }
    }

    /**
     * Тестовая функция создает два потока - читателя и писателя - и запускает их.
     * @param args Не используется.
     */
    public static void main (String[] args) {
        final Buffer buffer = new Buffer();

        final Reader reader = new Reader(buffer);
        final Writer writer = new Writer(buffer);

        reader.setWriterThread(writer);
        writer.setReaderThread(reader);

        reader.start();
        writer.start();
    }
}
