package org.tutorhub.entities.studyCenter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.tutorhub.annotations.entity.object.EntityAnnotations;
import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

@SuppressWarnings( value = "учебный центр" )
@Entity( name = PostgreSqlTables.STUDY_CENTER )
@Table(
        name = PostgreSqlTables.STUDY_CENTER,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.STUDY_CENTER,
        tableName = PostgreSqlTables.STUDY_CENTER,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "учебный центр"
)
public final class StudyCenter implements EntityToPostgresConverter {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Size(
            min = 5,
            max = 200,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Column(
            length = 200,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String name;

    @Size(
            min = 5,
            max = 200,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Column(
            length = 200,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String address;
}
