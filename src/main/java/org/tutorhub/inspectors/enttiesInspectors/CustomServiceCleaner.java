package org.tutorhub.inspectors.enttiesInspectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.inspectors.LogInspector;
import org.tutorhub.interfaces.services.ServiceCommonMethods;

import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import java.util.concurrent.atomic.AtomicReference;
import java.lang.ref.WeakReference;
import java.util.List;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class CustomServiceCleaner extends LogInspector {
    protected CustomServiceCleaner () {
        super( CustomServiceCleaner.class );
    }

    @EntityConstructorAnnotation( permission = AnnotationInspector.class )
    protected <T extends UuidInspector> CustomServiceCleaner ( @lombok.NonNull final Class<T> instance ) {
        super( CustomServiceCleaner.class );

        AnnotationInspector.checkCallerPermission( instance, CustomServiceCleaner.class );
        AnnotationInspector.checkAnnotationIsImmutable( CustomServiceCleaner.class );
    }

    @lombok.Synchronized
    protected static synchronized <T> void clearReference ( @lombok.NonNull final WeakReference< T > reference ) {
        reference.enqueue();
        reference.clear();
    }

    @lombok.Synchronized
    protected final synchronized <T> void clearReferences ( @lombok.NonNull final WeakReference< List<T> > reference ) {
        reference.get().clear();
        reference.enqueue();
        reference.clear();
    }

    @lombok.Synchronized
    protected final synchronized <T extends ServiceCommonMethods> void clearReference (
            @lombok.NonNull final AtomicReference< T > reference
    ) {
        reference.get().close();
    }

    @SafeVarargs
    @lombok.Synchronized
    protected final synchronized <T extends ServiceCommonMethods> void clearReference (
            @lombok.NonNull final T ... references
    ) {
        super.analyze(
                references,
                super::objectIsNotNull,
                ServiceCommonMethods::close
        );
    }
}
