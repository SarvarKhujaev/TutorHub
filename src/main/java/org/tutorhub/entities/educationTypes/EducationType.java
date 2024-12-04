package org.tutorhub.entities.educationTypes;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

import java.util.Date;

@Entity( name = PostgreSqlTables.EDUCATION_TYPES )
@Table(
        name = PostgreSqlTables.EDUCATION_TYPES,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.EDUCATION_TYPES,
        tableName = PostgreSqlTables.EDUCATION_TYPES,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = """
                отвечает за тип обучения которая предпочтительна
                для студента или учителя

                Например:
                    online, offline и т.д
                """
)
public final class EducationType implements EntityToPostgresConverter {
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

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 50 )",
            updatable = false,
            nullable = false,
            unique = true,
            length = 50
    )
    private String name;

    public EducationType () {}

    @EntityConstructorAnnotation
    public EducationType( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, EducationType.class );
    }
}
