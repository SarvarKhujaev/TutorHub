package org.tutorhub.inspectors;

import org.tutorhub.TutorHubApplication;
import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;

import java.util.function.Consumer;
import java.util.Optional;
import java.util.Objects;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class DataValidateInspector extends TimeInspector {
    protected DataValidateInspector () {
        super( DataValidateInspector.class );
    }

    @EntityConstructorAnnotation( permission = WebFluxInspector.class )
    protected <T extends UuidInspector> DataValidateInspector (@lombok.NonNull final Class<T> instance ) {
        super( DataValidateInspector.class );

        AnnotationInspector.checkCallerPermission( instance, DataValidateInspector.class );
        AnnotationInspector.checkAnnotationIsImmutable( DataValidateInspector.class );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public static synchronized <T> Optional<T> getOptional (
            final T object
    ) {
        return Optional.ofNullable( object );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected final synchronized boolean objectIsNotNull (
            final Object ... o
    ) {
        if ( o.length == 1 ) {
            return Objects.nonNull( o[0] );
        }

        byte index = 0;
        while ( index < o.length && Objects.nonNull( o[index] ) ) {
            index++;
        }

        return index == o.length;
    }

    @SuppressWarnings(
            value = """
                    принимает запись из БД в виде щбъектов Row или UdtValue
                    проверяет что запись не пуста
                    и заполняет параметры объекта по заданной логике
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    public static synchronized <T> void checkAndSetParams (
            final T object,
            @lombok.NonNull final Consumer< T > customConsumer
    ) {
        getOptional( object ).ifPresent( customConsumer );
    }

    @SuppressWarnings(
            value = """
                    получает в параметрах название параметра из файла application.yaml
                    проверят что context внутри main класса GpsTabletsServiceApplication  инициализирован
                    и среди параметров сервиса существует переданный параметр
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    protected static synchronized int checkContextOrReturnDefaultValue (
            @lombok.NonNull final String paramName,
            final int defaultValue
    ) {
        return Objects.nonNull( TutorHubApplication.context )
                && Objects.nonNull(
                        TutorHubApplication
                                .context
                                .getEnvironment()
                                .getProperty( paramName )
                )
                ? Integer.parseInt(
                        TutorHubApplication
                                .context
                                .getEnvironment()
                                .getProperty( paramName )
                )
                : defaultValue;
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected final synchronized int checkDifference ( final int integer ) {
        return integer > 0 && integer < 100 ? integer : 10;
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    protected static synchronized String checkContextOrReturnDefaultValue (
            @lombok.NonNull final String paramName,
            @lombok.NonNull final String defaultValue
    ) {
        return Objects.nonNull( TutorHubApplication.context )
                && Objects.nonNull(
                        TutorHubApplication
                                .context
                                .getEnvironment()
                                .getProperty( paramName )
                )
                ? TutorHubApplication
                        .context
                        .getEnvironment()
                        .getProperty( paramName )
                : defaultValue;
    }
}