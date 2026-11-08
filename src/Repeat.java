import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, указывающая, сколько раз нужно вызвать метод.
 * RUNTIME — аннотация доступна во время выполнения через Reflection API.
 * METHOD — аннотацию можно применять только к методам.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Repeat {
    int times();
}
