package org.tutorhub.entities.studyCenter;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.entities.teacher.Teacher;
import org.tutorhub.interfaces.database.EntityToPostgresConverter;
import org.tutorhub.annotations.entity.object.EntityAnnotations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.entities.subject.Subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Entity( name = PostgreSqlTables.COURSES )
@Table(
        name = PostgreSqlTables.COURSES,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.COURSES,
        tableName = PostgreSqlTables.COURSES,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "данные о курсах которые проводят в учебном центре"
)
public final class Course implements EntityToPostgresConverter {
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
    @Column(
            length = 200,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String name;

    @SuppressWarnings( value = "каждый курс связан с одним конкретным предметом" )
    @OneToOne(
            targetEntity = Subject.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Subject subject;

    @SuppressWarnings( value = "каждый курс связан с одним учителем который отвечает за этот курс" )
    @OneToOne(
            targetEntity = Teacher.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Teacher teacher;
}
