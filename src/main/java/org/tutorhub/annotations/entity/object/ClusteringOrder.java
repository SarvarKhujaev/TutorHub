package org.tutorhub.annotations.entity.object;

import java.lang.annotation.*;

@Target( value = ElementType.ANNOTATION_TYPE )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
@SuppressWarnings(
        value = """
                отвечает за Clustering key в таблице
                хранит информацию о названии колонки
                и в каком порядке сортировать эту колонку
                """
)
public @interface ClusteringOrder {
    String columnName();
}
