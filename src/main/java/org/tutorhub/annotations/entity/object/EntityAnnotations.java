package org.tutorhub.annotations.entity.object;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import java.lang.annotation.*;

@Target( value = ElementType.TYPE )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
public @interface EntityAnnotations {
    String name();
    String comment() default StringOperations.EMPTY;

    String tableName();
    String keysapceName();

    @SuppressWarnings(
            value = """
                    показывает можно ли при необходимости
                    как-либо менять параметр объекта
                    """
    )
    boolean canTouch() default true;
    @SuppressWarnings(
            value = """
                    показывает можно ли
                    прочитать содержимое параметра
                    при необходимости можно закрыть доступ к чтению

                    Параметр крайне похож на параметра canTouch
                    """
    )
    boolean isReadable() default true;
    @SuppressWarnings(
            value = """
                    показывает нужно является ли класс внутренним типом
                    для другого класса
                    """
    )
    boolean isSubClass() default false;
    @SuppressWarnings(
            value = """
                    показывает нужно ли перед каким-либо
                    запросом в БД проверять правильную структуру и наличие данного параметра
                    """
    )
    boolean checkExistence() default false;

    String[] primaryKeys() default { "id" };
    ClusteringOrder[] clusteringKeys() default {};
}
