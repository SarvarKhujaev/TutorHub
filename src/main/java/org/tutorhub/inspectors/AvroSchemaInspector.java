package org.tutorhub.inspectors;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;
import org.tutorhub.interfaces.kafka.KafkaEntitiesCommonMethods;

import org.tutorhub.annotations.avro.methods.AvroMethodAnnotation;
import org.tutorhub.annotations.avro.fields.AvroFieldAnnotation;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericData;
import org.apache.avro.Schema;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.CopyOnWriteArrayList;

import java.lang.reflect.InvocationTargetException;
import java.lang.ref.WeakReference;

@SuppressWarnings(
        value = """
                отвечает за работу с интерфейсом Schema библиотеки AVRO
                """
)
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class AvroSchemaInspector {
    private static final AtomicReference< CopyOnWriteArrayList< Schema.Field > > schemas = EntitiesInstances.generateAtomicEntity(
            CollectionsInspector.newList()
    );

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public static synchronized <T extends KafkaEntitiesCommonMethods> Schema generateSchema(
            @lombok.NonNull final T entity
    ) {
        schemas.getAndSet( CollectionsInspector.newList() );

        CollectionsInspector.analyze(
                AnnotationInspector
                        .getFields( entity.getClass() )
                        .filter( field -> field.isAnnotationPresent( AvroFieldAnnotation.class ) )
                        .map( field -> field.getAnnotation( AvroFieldAnnotation.class ) ),
                avroFieldAnnotation -> schemas.get().add(
                        new Schema.Field(
                                avroFieldAnnotation.name(),
                                Schema.create( avroFieldAnnotation.schemaType() ),
                                avroFieldAnnotation.description(),
                                StringOperations.AVRO_DATE_PATTERN
                        )
                )
        );

        return Schema.createRecord(
                entity.getClass().getCanonicalName(),
                AnnotationInspector.getKafkaTopicName( entity ).name(),
                entity.getClass().getPackageName(),
                false,
                schemas.get()
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public static synchronized <T extends KafkaEntitiesCommonMethods> GenericRecord generateGenericRecord (
            @lombok.NonNull final T entity
    ) {
        final WeakReference< GenericRecord > genericRecord = EntitiesInstances.generateWeakEntity(
                new GenericData.Record( generateSchema( entity ) )
        );

        CollectionsInspector.analyze(
                AnnotationInspector
                        .getMethods( entity.getClass() )
                        .filter( method -> method.isAnnotationPresent( AvroMethodAnnotation.class ) ),
                method -> {
                    try {
                        genericRecord.get().put(
                                method.getAnnotation( AvroMethodAnnotation.class ).name(),
                                method.invoke( entity )
                        );
                    } catch ( final InvocationTargetException | IllegalAccessException e ) {
                        System.out.println( e.getMessage() );
                    }
                }
        );

        return genericRecord.get();
    }

    public static void close () {
        CollectionsInspector.checkAndClear( schemas.get() );
    }
}
