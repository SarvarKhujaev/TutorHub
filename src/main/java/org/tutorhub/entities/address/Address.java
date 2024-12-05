package org.tutorhub.entities.address;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;

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
import org.hibernate.annotations.Check;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

import java.util.Date;

@Entity( name = PostgreSqlTables.ADDRESS )
@Table(
        name = PostgreSqlTables.ADDRESS,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.ADDRESS,
        tableName = PostgreSqlTables.ADDRESS,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "отвечает за хранение данных об адресе"
)
@Check( constraints = PostgresConstraintsValues.ADDRESS_TABLE_LOCATION_CONSTRAINT )
public final class Address implements EntityToPostgresConverter {
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
            max = 200,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @PartitionKey
    @Column(
            length = 200,
            unique = true,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String address;

    @Size(
            min = 60,
            max = 70,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @Column
    private double latitude;

    @Size(
            min = 60,
            max = 70,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @Column
    private double longitude;

    public Address () {}

    @EntityConstructorAnnotation
    public Address ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Address.class );
    }
}
