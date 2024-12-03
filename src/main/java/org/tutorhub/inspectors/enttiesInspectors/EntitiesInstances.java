package org.tutorhub.inspectors.enttiesInspectors;

import org.tutorhub.constans.postgres_constants.postgres_extensions.PostgresExtensions;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.response.ApiResponseModel;
import org.tutorhub.response.Status;
import org.tutorhub.response.Data;

import org.tutorhub.entities.student.Student;
import org.tutorhub.entities.teacher.Teacher;
import org.tutorhub.entities.comment.Comment;
import org.tutorhub.entities.lesson.Lesson;
import org.tutorhub.entities.group.Group;

import org.apache.commons.collections4.list.UnmodifiableList;
import org.apache.commons.collections4.set.UnmodifiableSet;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.atomic.AtomicReference;
import java.lang.ref.WeakReference;

import java.util.List;
import java.util.Set;

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


    public static final AtomicReference< Teacher > TEACHER_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Teacher( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Student > STUDENT_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Student( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Group > GROUP_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Group( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Lesson > LESSON_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Lesson( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Comment > COMMENT_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Comment( EntitiesInstances.class )
            )
    );


    @SuppressWarnings( value = "объекты для работы с Request и Response" )
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

    public static final Set< String > MATERIALIZED_VIEWS = UnmodifiableSet.unmodifiableSet(
            Set.of()
    );

    public static final Set< String > PREPARED_STATEMENTS = UnmodifiableSet.unmodifiableSet(
            Set.of()
    );

    public static final Set< String > CACHE_REGIONS_NAMES = UnmodifiableSet.unmodifiableSet(
            Set.of()
    );

    public static final Set< String > INDEX_CREATE_QUIRES = UnmodifiableSet.unmodifiableSet(
            Set.of()
    );

    public static final Set< String > TABLES_FOR_STATE_NAMES = UnmodifiableSet.unmodifiableSet(
            Set.of()
    );

    public static final Set< String > POSTGRES_EXTENSIONS = UnmodifiableSet.unmodifiableSet(
            Set.of(
                    PostgresExtensions.CREATE_EXTENSION_PG_PREWARM,
                    PostgresExtensions.CREATE_EXTENSION_PG_STAT_TUPLE,
                    PostgresExtensions.CREATE_EXTENSION_FOR_BUFFER_READ,
                    PostgresExtensions.CREATE_EXTENSION_PG_STAT_STATEMENTS
            )
    );

    @SuppressWarnings( value = "хранит instance на все основные объекты" )
    public static final UnmodifiableList< AtomicReference< ? extends EntityToPostgresConverter > > instancesList = new UnmodifiableList<>(
            List.of(
                    GROUP_ATOMIC_REFERENCE,
                    LESSON_ATOMIC_REFERENCE,
                    COMMENT_ATOMIC_REFERENCE,
                    TEACHER_ATOMIC_REFERENCE,
                    STUDENT_ATOMIC_REFERENCE
            )
    );

    public static void clear() {
        KAFKA_BYTE_SERIALIZER.get().close();
        KAFKA_STRING_SERIALIZER.get().close();
    }
}
