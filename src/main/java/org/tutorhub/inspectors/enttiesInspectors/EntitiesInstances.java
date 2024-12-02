package org.tutorhub.inspectors.enttiesInspectors;

import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.response.ApiResponseModel;
import org.tutorhub.response.Status;
import org.tutorhub.response.Data;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.atomic.AtomicReference;
import java.lang.ref.WeakReference;
import java.util.List;

@SuppressWarnings( value = "хранит instance на все объекты которые используются во время работы сервиса" )
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class EntitiesInstances extends AnnotationInspector {
    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized <T> WeakReference<T> generateWeakEntity ( final T entity ) {
        return new WeakReference<>( entity );
    }

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized <T> WeakReference< List<T> > generateWeakEntity () {
        return new WeakReference<>( emptyList() );
    }

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized <T> AtomicReference<T> generateAtomicEntity ( @lombok.NonNull final T entity ) {
        Validate.notNull( entity, NULL_VALUE_IN_ASSERT );
        return new AtomicReference<>( entity );
    }

    @SuppressWarnings(
            value = "объекты для работы с Request и Response"
    )
    public static final AtomicReference< Status > STATUS_ATOMIC_REFERENCE = generateAtomicEntity( Status.builder().build() );
    public static final AtomicReference< Data< Object, Object > > DATA_ATOMIC_REFERENCE = generateAtomicEntity( Data.from( StringOperations.EMPTY ) );
    public static final AtomicReference< ApiResponseModel > API_RESPONSE_MODEL_ATOMIC_REFERENCE = generateAtomicEntity( ApiResponseModel.builder().build() );

    @SuppressWarnings( value = "параметры для Кафки" )
    @WeakReferenceAnnotation( name = "KAFKA_STRING_SERIALIZER", isCollection = false, isWeak = true )
    public static final WeakReference< org.apache.kafka.common.serialization.StringSerializer > KAFKA_STRING_SERIALIZER = generateWeakEntity(
            new org.apache.kafka.common.serialization.StringSerializer()
    );

    @WeakReferenceAnnotation( name = "KAFKA_BYTE_SERIALIZER", isCollection = false, isWeak = true )
    public static final WeakReference< org.apache.kafka.common.serialization.ByteArraySerializer > KAFKA_BYTE_SERIALIZER = generateWeakEntity(
            new org.apache.kafka.common.serialization.ByteArraySerializer()
    );

    public static void clear() {
        KAFKA_BYTE_SERIALIZER.get().close();
        KAFKA_STRING_SERIALIZER.get().close();
    }
}
