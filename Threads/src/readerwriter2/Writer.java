package readerwriter2;

/**
 * Реализация процесса-писателя. Он знает о наличии читателя и посылает ему сообщение (прерывание),
 * когда записывает число.
 */
public class Writer extends Thread {
    // Буфер, в который пишутся сообщения.
    final Buffer buffer;
    // Процесс-читатель, которого надо уведомить после записи.
    Thread reader = null;

    // Конструктор.
    public Writer(Buffer buffer) { this.buffer = buffer; }

    /**
     * Запоминает ссылку на процесс-читатель.
     * @param reader Процесс-читатель.
     */
    public void setReaderThread(Thread reader) { this.reader = reader; }

    /**
     * Запись.
     * @param n Записываемое число.
     */
    public void write(Integer n) {
        buffer.addObject(n);
        // Извещаем процесс-читатель о том, что есть новое значение.
        reader.interrupt();
    }

    /**
     * Основная функция потока пишет 1000 чисел в буфер.
     */
    @Override
    public void run() {
        // Перед началом работы процесс читатель должен существовать.
        assert(reader != null);
        for (int i = 0; i < 1000; i++) {
            // Имитируем подготовку очередного значения.
            for (int k = 0, sum = 0; k < 10000; ++k) { sum += k; }
            //try { Thread.sleep(2); } catch (InterruptedException x) {}
            // Записываем число в буфер.
            write(i);
        }
        // Конец работы.
        System.out.println("Writer done");
    }
}
