package org.tutorhub.entities.educationDirection;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Entity( name = PostgreSqlTables.EDUCATION_DIRECTIONS )
@Table(
        name = PostgreSqlTables.EDUCATION_DIRECTIONS,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.EDUCATION_DIRECTIONS,
        tableName = PostgreSqlTables.EDUCATION_DIRECTIONS,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "название направления по которым проводятся занятия"
)
public final class EducationDirection implements EntityToPostgresConverter {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @Immutable
    @PartitionKey
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "direction_name",
            unique = true,
            nullable = false,
            updatable = false,
            columnDefinition = "VARCHAR( 50 )"
    )
    private String directionName;

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

    public EducationDirection () {}

    @EntityConstructorAnnotation
    public EducationDirection ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, EducationDirection.class );
    }
}
