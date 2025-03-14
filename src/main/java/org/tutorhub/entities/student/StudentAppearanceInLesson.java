package org.tutorhub.entities.student;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;
import org.tutorhub.annotations.LinksToDocs;

import org.tutorhub.constans.entities_constants.lesson.LessonAppearanceTypes;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity( name = PostgreSqlTables.STUDENT_APPEARANCE_IN_LESSONS )
@Table(
        name = PostgreSqlTables.STUDENT_APPEARANCE_IN_LESSONS,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.STUDENT_APPEARANCE_IN_LESSONS,
        tableName = PostgreSqlTables.STUDENT_APPEARANCE_IN_LESSONS,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = """
                хранит данные о том, какие занятия посещал студент
                """
)
public final class StudentAppearanceInLesson implements EntityToPostgresConverter {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Immutable
    @PartitionKey
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "created_date",
            nullable = false,
            updatable = false,
            columnDefinition = PostgreSqlFunctions.NOW
    )
    private final Date createdDate = TimeInspector.newDate();

    @LinksToDocs( links = "https://www.baeldung.com/jpa-default-column-values" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Enumerated( value = EnumType.STRING )
    @PartitionKey
    @Column(
            name = "lesson_appearance_type",
            length = 30,
            nullable = false,
            columnDefinition = "VARCHAR( 30 ) DEFAULT 'ABSENT'"
    )
    private LessonAppearanceTypes lessonAppearanceTypes = LessonAppearanceTypes.ABSENT;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToOne(
            targetEntity = Student.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.STUDENT_APPEARANCE_IN_LESSONS + "_student", isCollection = false )
    private Student student;

    public StudentAppearanceInLesson () {}

    @EntityConstructorAnnotation
    public StudentAppearanceInLesson ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, StudentAppearanceInLesson.class );
    }
}
