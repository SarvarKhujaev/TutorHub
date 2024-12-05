package org.tutorhub.entities.homework;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Entity( name = PostgreSqlTables.HOMEWORK )
@Table(
        name = PostgreSqlTables.HOMEWORK,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.HOMEWORK,
        tableName = PostgreSqlTables.HOMEWORK,
        keysapceName = PostgreSqlSchema.ENTITIES
)
public final class Homework implements EntityToPostgresConverter {
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
}
