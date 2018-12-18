package structuries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListTest {

  @Test
  public void addFirst() {

  }

  @Test
  public void addLast() {
  }

  @Test
  public void getAt() {
    List<String> test = new List<>();
    test.addFirst("test");
    test.addFirst("test1");
    test.addFirst("test2");
    test.addFirst("test3");
    test.addFirst("test4");
    test.addFirst("test5");

    assertEquals("test2", test.getAt(2));
    assertEquals("test2", test.getAt(6));

  }
}
