package Locks;
/**
 * Реализация процесса-писателя. Он ничего не знает о наличии читателя.
 */
public class Writer extends Thread {
  // Буфер, в который пишутся сообщения.
  final Buffer buffer;
  
  // Конструктор.
  public Writer(Buffer buffer) { this.buffer = buffer; }

  /**
   * Запись.
   * @param n Записываемое число.
   */
  public void write(Integer n) {
    buffer.addObject(n);
  }
  
  /**
   * Основная функция потока пишет 1000 чисел в буфер.
   */
  @Override
  public void run() {
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
