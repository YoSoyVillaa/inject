package me.yushust.inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

public class InstanceBindingTest {

  @Test
  public void test() {

    Baz baz = new Baz(); // not injected, the injector doesn't know about this instance
    Assertions.assertNull(baz.boo);

    Injector injector = Injector.create(binder ->
        binder.bind(Baz.class).toInstance(baz)
    );
    Baz baz2 = injector.getInstance(Baz.class);

    Assertions.assertSame(baz, baz2);
    Assertions.assertNotNull(baz.boo);
    Assertions.assertSame(baz, baz.boo.baz);
  }

  public static class Baz {
    @Inject private Boo boo;
  }

  public static class Boo {
    @Inject private Baz baz;
  }

}