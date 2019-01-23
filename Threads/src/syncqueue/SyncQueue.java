package syncqueue;

/**
 * Простая очередь целых с операциями, защищенными от ошибок синхронизации.
 */
public class SyncQueue {
    /**
     * Номер последнего добавленного элемента очереди
     */
    private int last = 0;

    /**
     * Узел очереди.
     */
    private static class QueueNode {
        /**
         * Содержание узла - целое число
         */
        private int info;

        /**
         * Ссылка на следующий узел
         */
        private QueueNode next = null;
    }

    /**
     * Голова очереди
     */
    private QueueNode head = null;

    /**
     * Конец очереди
     */
    private QueueNode tail = null;

    /**
     * Добавляет в очередь очередной элемент и записывает его номер.
     * @return Номер очередного добавленного элемента
     */
    public synchronized int add() {
        QueueNode newNode = new QueueNode();
        newNode.info = ++last;
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        return last;
    }

    /**
     * Удаляет из очереди первый элемент.
     * @return Значение удаленного элемента.
     */
    public synchronized int get() {
        if (head == null) return 0;
        int result = head.info;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
        }
        return result;
    }
}
