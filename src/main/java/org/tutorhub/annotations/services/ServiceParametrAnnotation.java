package org.tutorhub.annotations.services;

import java.lang.annotation.*;

@Target( value = ElementType.TYPE )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
@SuppressWarnings(
        value = """
                используется на уровне сервисов для взаимодействия с appliation.yml,
                хранит основное название переменных
                """
)
public @interface ServiceParametrAnnotation {
    @SuppressWarnings(
            value = "название группы для самого сервиса где храняться все переменные этого сервиса"
    )
    String propertyGroupName() default "JWT_VARIABLES";
    @SuppressWarnings(
            value = "название основной группы где храняться все переменные"
    )
    String mainGroupName() default "variables";
}
