package org.tutorhub.entities.lesson;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import jakarta.validation.constraints.*;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import org.hibernate.annotations.*;
import java.util.Date;

@Entity( name = PostgreSqlTables.LESSON_NOTES )
@Table(
        name = PostgreSqlTables.LESSON_NOTES,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.LESSON_NOTES,
        tableName = PostgreSqlTables.LESSON_NOTES,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = """
                хранит заметки для урока оставленные преподом
                """
)
public final class LessonNotes implements EntityToPostgresConverter {
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

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Column(
            length = 200,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String note;

    public LessonNotes () {}

    @EntityConstructorAnnotation
    public LessonNotes ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, LessonNotes.class );
    }
}
