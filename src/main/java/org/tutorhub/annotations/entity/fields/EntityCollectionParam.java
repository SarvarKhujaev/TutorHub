package org.tutorhub.annotations.entity.fields;

import java.lang.annotation.*;

@Target( value = ElementType.FIELD )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
public @interface EntityCollectionParam {
    String name();

    boolean isFrozen() default false;
}
