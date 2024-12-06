package org.tutorhub.entities.wishAndPlans;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.constans.entities_constants.wishes.WishCompletionTypes;
import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jakarta.persistence.Table;
import jakarta.persistence.*;

import org.hibernate.annotations.*;
import java.util.Date;

@Entity( name = PostgreSqlTables.FUTURE_PLANS_AND_WISHES )
@Table(
        name = PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
        tableName = PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "хранит стадии выполнения желаний и планов у учителей и студентов"
)
public final class FuturePlan implements EntityToPostgresConverter {
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

    @SuppressWarnings( value = "описание пожелания или планов" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Size(
            max = 200,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @Column(
            columnDefinition = "VARCHAR( 200 )",
            nullable = false,
            length = 200,
            name = "wish_description"
    )
    private String wishDescription;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Enumerated( value = EnumType.STRING )
    @PartitionKey
    @Column(
            name = "lesson_status",
            length = 30,
            nullable = false,
            columnDefinition = "VARCHAR( 30 ) DEFAULT 'NOT_COMPLETED'"
    )
    private WishCompletionTypes wishCompletionTypes = WishCompletionTypes.NOT_COMPLETED;

    public FuturePlan() {}

    @EntityConstructorAnnotation
    public FuturePlan( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, FuturePlan.class );
    }
}
