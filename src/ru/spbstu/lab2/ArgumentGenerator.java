package ru.spbstu.lab2;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

// Подбирает значения для параметров любого типа
public class ArgumentGenerator {
    // защита от зацикливания, например если конструктор класса принимает объект этого же класса
    private static final int MAX_DEPTH = 5;
    private static final int ARRAY_LENGTH = 3;

    private final Random random = new Random();

    public Object[] generate(Class<?>[] types) throws ReflectiveOperationException {
        Object[] values = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            values[i] = generate(types[i], 0);
        }
        return values;
    }

    private Object generate(Class<?> type, int depth) throws ReflectiveOperationException {
        if (type == int.class || type == Integer.class) {
            return random.nextInt(100);
        }
        if (type == long.class || type == Long.class) {
            return (long) random.nextInt(10000);
        }
        if (type == short.class || type == Short.class) {
            return (short) random.nextInt(100);
        }
        if (type == byte.class || type == Byte.class) {
            return (byte) random.nextInt(100);
        }
        if (type == double.class || type == Double.class) {
            return random.nextDouble() * 100;
        }
        if (type == float.class || type == Float.class) {
            return random.nextFloat() * 100;
        }
        if (type == boolean.class || type == Boolean.class) {
            return random.nextBoolean();
        }
        if (type == char.class || type == Character.class) {
            return (char) ('A' + random.nextInt(26));
        }
        if (type == String.class) {
            return "строка" + random.nextInt(100);
        }
        if (type.isEnum()) {
            Object[] constants = type.getEnumConstants();
            return constants.length == 0 ? null : constants[random.nextInt(constants.length)];
        }
        if (type.isArray()) {
            Object array = Array.newInstance(type.getComponentType(), ARRAY_LENGTH);
            for (int i = 0; i < ARRAY_LENGTH; i++) {
                Array.set(array, i, generate(type.getComponentType(), depth + 1));
            }
            return array;
        }
        // экземпляр интерфейса или абстрактного класса создать нельзя
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            return null;
        }
        if (depth >= MAX_DEPTH) {
            return null;
        }
        return createObject(type, depth);
    }

    private Object createObject(Class<?> type, int depth) throws ReflectiveOperationException {
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        // сначала конструктор без параметров, если его нет, то с наименьшим числом параметров
        Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));

        for (Constructor<?> constructor : constructors) {
            if (!constructor.trySetAccessible()) {
                continue;
            }
            Object[] values = new Object[constructor.getParameterCount()];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                values[i] = generate(parameterTypes[i], depth + 1);
            }
            try {
                return constructor.newInstance(values);
            } catch (InvocationTargetException e) {
                // конструктор не принял сгенерированные значения, пробуем следующий
            }
        }
        // ни одним конструктором создать объект не получилось
        return null;
    }
}
