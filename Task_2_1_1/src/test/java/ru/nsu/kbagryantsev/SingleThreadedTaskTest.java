package ru.nsu.kbagryantsev;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SingleThreadedTaskTest {
    @Test
    @DisplayName("Coverage plug")
    void coveragePlug() throws
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<SingleThreadedTask> constructor;
        constructor = SingleThreadedTask.class.getDeclaredConstructor();

        constructor.setAccessible(true);
        constructor.newInstance();
        Assertions.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    @DisplayName("All numbers are prime")
    void allPrime() {
        List<Integer> data = List.of(13, 23, 97, 3, 127373);

        Assertions.assertFalse(SingleThreadedTask.call(data));
    }

    @Test
    @DisplayName("One of numbers is composite")
    void oneComposite() {
        List<Integer> data = List.of(13, 23, 97, 6, 127373);

        Assertions.assertTrue(SingleThreadedTask.call(data));
    }
}
