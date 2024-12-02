package org.tutorhub.annotations.sqlInjection;

import java.lang.annotation.*;

@Target( value = ElementType.FIELD )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
@SuppressWarnings(
        value = """
                используется для помечания параметров классов
                которые могут содержать SQL инъекции
                """
)
public @interface SqlInjectionDanger {
}
