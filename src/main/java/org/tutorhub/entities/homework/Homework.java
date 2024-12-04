package org.tutorhub.entities.homework;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.PartitionKey;
import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;
import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraints;

import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import java.util.Date;


@Entity( name = PostgreSqlTables.HOMEWORK )
@Table(
        name = PostgreSqlTables.HOMEWORK,
        schema = PostgreSqlSchema.ENTITIES
)
@Cacheable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE,
        region = HibernateCacheRegions.TEACHER_REGION
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
