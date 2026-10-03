package ru.spbstu.lab2;

import ru.spbstu.lab2.team.RaceEngineer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

    public static void main(String[] args) {
        RaceEngineer engineer = new RaceEngineer("Джанпьеро");
        ArgumentGenerator generator = new ArgumentGenerator();

        for (Method method : RaceEngineer.class.getDeclaredMethods()) {
            Repeat repeat = method.getAnnotation(Repeat.class);
            if (repeat == null) {
                continue;
            }
            int modifiers = method.getModifiers();
            if (!Modifier.isProtected(modifiers) && !Modifier.isPrivate(modifiers)) {
                continue;
            }

            System.out.println("Метод " + method.getName() + ", вызовов: " + repeat.value());
            if (!method.trySetAccessible()) {
                System.out.println("Нет доступа к методу " + method.getName());
                continue;
            }
            for (int i = 0; i < repeat.value(); i++) {
                try {
                    method.invoke(engineer, generator.generate(method.getParameterTypes()));
                } catch (InvocationTargetException e) {
                    System.out.println("Метод " + method.getName() + " выбросил исключение: " + e.getCause());
                } catch (ReflectiveOperationException e) {
                    System.out.println("Не удалось вызвать метод " + method.getName() + ": " + e);
                }
            }
            System.out.println();
        }
    }
}
