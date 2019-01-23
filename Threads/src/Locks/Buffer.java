package Locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Реализация задачи о производителе и потребителе.
 * Это решение не приводит к тупиковой ситуации, оно использует механизм 
 * объектов высокого уровня Lock и Condition.
 */
public class Buffer {
  final int MAX_COUNT = 10;
  // Буфер на 10 целых чисел.
  final private Integer[] buffer = new Integer[MAX_COUNT];
  // Указывает на первую занятую ячейку.
  private int first = 0;
  // Число занятых ячеек.
  private int count = 0;
  
  final Lock lock = new ReentrantLock();
  final Condition hasItem = lock.newCondition();
  final Condition hasFreeCell = lock.newCondition();
  
  /**
   * Записывает число в буфер.
   * @param n Записываемое число.
   */
  public void addObject(Integer n) {
    lock.lock();
    try {
      // Пороверяем, есть ли место, куда можно записать очередное значение.
      while (count == MAX_COUNT) {
        try {
          // Разблокируем буфер и ждем наступления события.
          hasFreeCell.await();
        } catch (InterruptedException x) {}
      }
      // Записываем очередное число в буфер.
      buffer[(first + count++) % MAX_COUNT] = n;
      // Извещаем всех о наступлении события "в буфере есть элемент".
      hasItem.signal();
    } finally {
      lock.unlock();
    }
  }
  
  /**
   * Чтение числа из буфера с удалением.
   * @return Прочитанное число.
   */
  public Integer removeObject() {
    lock.lock();
    try {
      // Проверяем, есть ли в буфере хотя бы одно значение.
      while (count == 0) {
        try {
          // Разблокируем буфер и ждем наступления события.
          hasItem.await();
        } catch (InterruptedException x) {}
      }
      Integer toRemove = buffer[first++];
      first %= MAX_COUNT;
      count--;
      // Извещаем всех о наступлении события "в буфере есть место".
      hasFreeCell.signal();
      return toRemove;
    } finally {
      lock.unlock();
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
    
    reader.start();
    writer.start();
  }
}
