package org.tutorhub.annotations.entity.object;

import java.lang.annotation.*;

@Target( value = ElementType.TYPE )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
@SuppressWarnings(
        value = """
                хранит все таблицы, с которыми связана сущность
                Например:
                    Patrul -> ReqCar
                """
)
public @interface EntityJoinedTables {
}
