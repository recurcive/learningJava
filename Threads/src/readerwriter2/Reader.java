package readerwriter2;

/**
 * Реализация процесса-читателя. Он знает о наличии писателя и посылает ему сообщение (прерывание),
 * когда освобождает ячейку
 */
public class Reader extends Thread {
    // Буфер, из которого читаются сообщения.
    final Buffer buffer;
    // Процесс-писатель, которого надо уведомить после чтения.
    Thread writer = null;

    // Конструктор.
    public Reader(Buffer buffer) { this.buffer = buffer; }

    /**
     * Запоминает ссылку на процесс-писатель.
     * @param writer Процесс-писатель.
     */
    public void setWriterThread(Thread writer) { this.writer = writer; }

    /**
     * Чтение.
     * @return Прочитанное число.
     */
    public Integer read() {
        Integer r = buffer.removeObject();
        // Извещаем процесс-писатель о том, что освободилось место.
        writer.interrupt();
        return r;
    }

    /**
     * Основная функция потока читает 1000 чисел из буфера.
     */
    @Override
    public void run() {
        // Перед началом работы процесс писатель должен существовать.
        assert(writer != null);
        for (int i = 0; i < 1000; i++) {
            Integer result = read();
            // Имитируем обработку прочитанного числа.
            for (int k = 0, sum = 0; k < 10000; ++k) { sum += k; }
            // Очередное прочитанное число должно быть тем, которое мы записали.
            if (result.intValue() != i) {
                System.out.println("Expected " + i + " but " + result + " received.");
            }
        }
        // Конец работы.
        System.out.println("Reader done");
    }
}
