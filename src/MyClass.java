/**
 * Класс с публичными, защищёнными и приватными методами.
 * Часть методов помечена аннотацией @Repeat с разными значениями times.
 */
public class MyClass {

    // ---------- Публичные методы ----------

    @Repeat(times = 5) // публичный: Invoker его НЕ вызывает (только protected и private)
    public void publicHello() {
        System.out.println("public publicHello()");
    }

    public int publicSum(int a, int b) {
        int result = a + b;
        System.out.println("public publicSum(" + a + ", " + b + ") = " + result);
        return result;
    }

    public String publicUpper(String text) {
        String result = text.toUpperCase();
        System.out.println("public publicUpper(\"" + text + "\") = " + result);
        return result;
    }

    // ---------- Защищённые методы ----------

    @Repeat(times = 2)
    protected void protectedGreet(String name) {
        System.out.println("protected protectedGreet: Привет, " + name + "!");
    }

    @Repeat(times = 3)
    protected double protectedMultiply(double a, double b) {
        double result = a * b;
        System.out.println("protected protectedMultiply(" + a + ", " + b + ") = " + result);
        return result;
    }

    protected void protectedNotAnnotated() {
        System.out.println("protected protectedNotAnnotated() — не должен вызываться");
    }

    // ---------- Приватные методы с параметрами ----------

    @Repeat(times = 1)
    private int privatePower(int base, int exponent) {
        int result = (int) Math.pow(base, exponent);
        System.out.println("private privatePower(" + base + ", " + exponent + ") = " + result);
        return result;
    }

    @Repeat(times = 4)
    private void privateConcat(String first, String second, char separator) {
        System.out.println("private privateConcat: " + first + separator + second);
    }

    private boolean privateIsEven(long number) {
        boolean result = number % 2 == 0;
        System.out.println("private privateIsEven(" + number + ") = " + result + " — не должен вызываться");
        return result;
    }
}
