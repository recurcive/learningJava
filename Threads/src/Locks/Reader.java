package Locks;
/**
 * Реализация процесса-читателя. Он ничего не знает о наличии писателя.
 */
public class Reader extends Thread {
  // Буфер, из которого читаются сообщения.
  final Buffer buffer;

  // Конструктор.
  public Reader(Buffer buffer) { this.buffer = buffer; }

  /**
   * Чтение.
   * @return Прочитанное число.
   */
  public Integer read() {
    return buffer.removeObject();
  }
  
  /**
   * Основная функция потока читает 1000 чисел из буфера.
   */
  @Override
  public void run() {
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
