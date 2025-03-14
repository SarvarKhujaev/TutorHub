package org.tutorhub.annotations.services;

import java.lang.annotation.*;

@Target( value = ElementType.TYPE )
@Retention( value = RetentionPolicy.RUNTIME )
@Documented
public @interface ImmutableEntityAnnotation {
}
