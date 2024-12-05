package org.tutorhub.entities.markAndRating;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;
import org.tutorhub.annotations.LinksToDocs;

import org.tutorhub.constans.entities_constants.homework.HomeworkMarkTypes;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Entity( name = PostgreSqlTables.HOMEWORK_MARKS )
@Table(
        name = PostgreSqlTables.HOMEWORK_MARKS,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.HOMEWORK_MARKS,
        tableName = PostgreSqlTables.HOMEWORK_MARKS,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "оценка которая ставиться для каждого дом. задания со стороны учителя"
)
public final class HomeworkMark implements EntityToPostgresConverter {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Immutable
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "created_date",
            nullable = false,
            updatable = false,
            columnDefinition = PostgreSqlFunctions.NOW
    )
    private final Date createdDate = TimeInspector.newDate();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            length = 200,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String comment;

    @LinksToDocs( links = "https://www.baeldung.com/jpa-default-column-values" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Enumerated( value = EnumType.STRING )
    @PartitionKey
    @Column(
            name = "homework_mark_types",
            length = 30,
            nullable = false,
            columnDefinition = "VARCHAR( 30 ) DEFAULT 'PERFECT'"
    )
    private HomeworkMarkTypes homeworkMarkTypes = HomeworkMarkTypes.PERFECT;

    public HomeworkMark () {}

    @EntityConstructorAnnotation
    public HomeworkMark ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, HomeworkMark.class );
    }
}
