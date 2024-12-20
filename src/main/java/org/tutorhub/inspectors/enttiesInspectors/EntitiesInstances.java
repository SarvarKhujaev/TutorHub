package org.tutorhub.inspectors.enttiesInspectors;

import org.tutorhub.constans.postgres_constants.postgres_extensions.PostgresExtensions;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.entities.studyCenter.statistics.StudentStudyInterval;
import org.tutorhub.entities.studyCenter.StudyCenter;
import org.tutorhub.entities.studyCenter.Course;

import org.tutorhub.entities.educationDirection.EducationDirection;
import org.tutorhub.entities.educationTypes.EducationType;

import org.tutorhub.entities.student.StudentAppearanceInLesson;
import org.tutorhub.entities.student.Student;

import org.tutorhub.entities.homework.SolvedHomework;
import org.tutorhub.entities.homework.Homework;

import org.tutorhub.entities.lesson.LessonNotes;
import org.tutorhub.entities.lesson.Lesson;

import org.tutorhub.entities.markAndRating.HomeworkMark;
import org.tutorhub.entities.wishAndPlans.FuturePlan;
import org.tutorhub.entities.teacher.Teacher;
import org.tutorhub.entities.comment.Comment;
import org.tutorhub.entities.address.Address;
import org.tutorhub.entities.subject.Subject;
import org.tutorhub.entities.group.Group;

import org.tutorhub.response.ApiResponseModel;
import org.tutorhub.response.Status;
import org.tutorhub.response.Data;

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

    public static final AtomicReference< StudyCenter > STUDY_CENTER_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new StudyCenter( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< EducationDirection > EDUCATION_DIRECTION_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new EducationDirection( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Course > COURSE_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Course( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Subject > SUBJECT_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Subject( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Address > ADDRESS_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Address( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< EducationType > EDUCATION_TYPE_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new EducationType( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< LessonNotes > LESSON_NOTES_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new LessonNotes( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< Homework > HOMEWORK_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new Homework( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< SolvedHomework > SOLVED_HOMEWORK_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new SolvedHomework( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< HomeworkMark > HOMEWORK_MARK_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new HomeworkMark( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< StudentStudyInterval > STUDENT_STUDY_INTERVAL_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new StudentStudyInterval( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< StudentAppearanceInLesson > STUDENT_APPEARANCE_IN_LESSON_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new StudentAppearanceInLesson( EntitiesInstances.class )
            )
    );

    public static final AtomicReference< FuturePlan > FUTURE_PLAN_ATOMIC_REFERENCE = generateAtomicEntity(
            checkAnnotationIsNotImmutable(
                    new FuturePlan( EntitiesInstances.class )
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
                    COURSE_ATOMIC_REFERENCE,
                    COMMENT_ATOMIC_REFERENCE,
                    TEACHER_ATOMIC_REFERENCE,
                    STUDENT_ATOMIC_REFERENCE,
                    SUBJECT_ATOMIC_REFERENCE,
                    ADDRESS_ATOMIC_REFERENCE,
                    HOMEWORK_ATOMIC_REFERENCE,
                    FUTURE_PLAN_ATOMIC_REFERENCE,
                    STUDY_CENTER_ATOMIC_REFERENCE,
                    LESSON_NOTES_ATOMIC_REFERENCE,
                    HOMEWORK_MARK_ATOMIC_REFERENCE,
                    EDUCATION_TYPE_ATOMIC_REFERENCE,
                    SOLVED_HOMEWORK_ATOMIC_REFERENCE,
                    EDUCATION_DIRECTION_ATOMIC_REFERENCE,
                    STUDENT_STUDY_INTERVAL_ATOMIC_REFERENCE,
                    STUDENT_APPEARANCE_IN_LESSON_ATOMIC_REFERENCE
            )
    );

    public static void clear() {
        KAFKA_BYTE_SERIALIZER.get().close();
        KAFKA_STRING_SERIALIZER.get().close();
    }
}
