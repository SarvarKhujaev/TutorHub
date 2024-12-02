package org.tutorhub.inspectors;

import org.tutorhub.inspectors.enttiesInspectors.CustomServiceCleaner;
import org.tutorhub.inspectors.sqlInjection.QueryBuilderInspector;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.springframework.scheduling.annotation.Async;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
@org.tutorhub.annotations.services.ServiceParametrAnnotation( propertyGroupName = "LOGGER_WITH_JSON_LAYOUT" )
public class LogInspector extends WebFluxInspector {
    protected LogInspector () {
        super( LogInspector.class );
    }

    @EntityConstructorAnnotation(
            permission = {
                    CustomServiceCleaner.class,
                    QueryBuilderInspector.class
            }
    )
    protected <T extends UuidInspector> LogInspector ( @lombok.NonNull final Class<T> instance ) {
        super( LogInspector.class );

        AnnotationInspector.checkCallerPermission( instance, LogInspector.class );
        AnnotationInspector.checkAnnotationIsImmutable( LogInspector.class );
    }

    private final static Logger LOGGER = LogManager.getLogger( "LOGGER_WITH_JSON_LAYOUT" );

    @Async( value = "LogInspector" )
    protected void logging ( @lombok.NonNull final Object o ) {
        LOGGER.info(
                String.join(
                        SPACE_WITH_DOUBLE_DOTS,
                        o.getClass().getName(),
                        "was closed successfully at",
                        super.newDate().toString()
                )
        );
    }

    @Async( value = "LogInspector" )
    protected void logging ( @lombok.NonNull final String message ) {
        LOGGER.info( message );
    }

    @Async( value = "LogInspector" )
    protected void logging ( @lombok.NonNull final Throwable error ) {
        LOGGER.error(
                String.join(
                        SPACE_WITH_DOUBLE_DOTS,
                        "Error",
                        error.getMessage()
                )
        );
    }

    @Async( value = "LogInspector" )
    protected void logging (
            @lombok.NonNull final Throwable error,
            @lombok.NonNull final Object o
    ) {
        LOGGER.error("Error: {} and reason: {}: ", error.getMessage(), o );
    }

    @Async( value = "LogInspector" )
    protected void logging ( @lombok.NonNull final Class<? extends CollectionsInspector> clazz ) {
        LOGGER.info(
                String.join(
                        SPACE_WITH_DOUBLE_DOTS,
                        clazz.getName(),
                        "was created at",
                        super.newDate().toString()
                )
        );
    }
}
