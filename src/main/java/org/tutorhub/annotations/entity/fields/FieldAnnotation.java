package org.tutorhub.annotations.entity.fields;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import java.lang.annotation.*;

@Target( value = ElementType.FIELD )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
public @interface FieldAnnotation {
    String name();
    String comment() default StringOperations.EMPTY;

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
    boolean mightBeNull() default true;
    @SuppressWarnings(
            value = """
                    показывает является параметр внутренним Type таблицыб
                    Например:
                        для таблицы Patruls -> PatrulCarInfo
                    """
    )
    boolean isInteriorObject() default false;
    @SuppressWarnings(
            value = """
                    показывает является ли параметр типом данных
                    который нужно облицивать ''
                    Например:
                        Text и Timestamp
                    """
    )
    boolean hasToBeJoinedWithAstrix() default false;
}
