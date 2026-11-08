import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Через Reflection API получает все методы MyClass и вызывает
 * защищённые и приватные методы с аннотацией @Repeat нужное число раз.
 */
public class Invoker {

    public static void main(String[] args) throws Exception {
        MyClass obj = new MyClass();

        // getDeclaredMethods() возвращает методы с любым модификатором доступа.
        // Сортируем по имени, т.к. порядок getDeclaredMethods() не гарантирован.
        Method[] methods = MyClass.class.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        System.out.println("Все методы класса MyClass:");
        for (Method method : methods) {
            System.out.printf("  %-10s %s%n", Modifier.toString(method.getModifiers()), method.getName());
        }
        System.out.println();

        for (Method method : methods) {
            int modifiers = method.getModifiers();
            boolean protectedOrPrivate = Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers);

            if (!protectedOrPrivate || !method.isAnnotationPresent(Repeat.class)) {
                continue;
            }

            int times = method.getAnnotation(Repeat.class).times();
            Object[] arguments = argumentsFor(method);

            System.out.println("=== " + Modifier.toString(modifiers) + " " + method.getName()
                    + Arrays.toString(arguments) + ", @Repeat(times = " + times + ") ===");

            method.setAccessible(true); // разрешаем вызов protected/private методов
            for (int i = 1; i <= times; i++) {
                System.out.print("[" + i + "/" + times + "] ");
                Object result = method.invoke(obj, arguments);
                if (method.getReturnType() != void.class) {
                    System.out.println("        вернул: " + result);
                }
            }
            System.out.println();
        }
    }

    /** Подбирает значения аргументов по типам параметров метода. */
    private static Object[] argumentsFor(Method method) {
        return switch (method.getName()) {
            case "protectedGreet" -> new Object[]{"Александр"};
            case "protectedMultiply" -> new Object[]{2.5, 4.0};
            case "privatePower" -> new Object[]{2, 10};
            case "privateConcat" -> new Object[]{"Карманов", "КБ-251", '-'};
            default -> defaultArguments(method.getParameterTypes());
        };
    }

    /** Значения по умолчанию для методов, для которых аргументы не заданы явно. */
    private static Object[] defaultArguments(Class<?>[] types) {
        Object[] values = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            Class<?> t = types[i];
            if (t == int.class) values[i] = 1;
            else if (t == long.class) values[i] = 1L;
            else if (t == double.class) values[i] = 1.0;
            else if (t == boolean.class) values[i] = true;
            else if (t == char.class) values[i] = 'a';
            else if (t == String.class) values[i] = "text";
            else values[i] = null;
        }
        return values;
    }
}
