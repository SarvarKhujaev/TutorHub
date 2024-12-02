package org.tutorhub.annotations.entity.constructor;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;
import java.lang.annotation.*;

@Target( value = ElementType.CONSTRUCTOR )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
@SuppressWarnings(
        value = """
                используется для того, чтобы показать
                какие классы имееют доступ к текущему объекту
                """
)
public @interface EntityConstructorAnnotation {
    @SuppressWarnings(
            value = """
                    перечень допустимых классов
                    которые могут вызывать конструктор текущего объекта
                    """
    )
    Class<?>[] permission() default EntitiesInstances.class;

    @SuppressWarnings(
            value = """
                    показывает используется ли
                    среди наследуемых классов строгая наследственность
                    через команду java sealed и permits
                    """
    )
    boolean isSealed() default false;
}
