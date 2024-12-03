package org.tutorhub.entities.comment;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;
import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraints;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.entities.student.Student;
import org.tutorhub.entities.lesson.Lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import java.util.Date;

@Entity( name = PostgreSqlTables.COMMENTS )
@Table(
        name = PostgreSqlTables.COMMENTS,
        schema = PostgreSqlSchema.ENTITIES
)
@Cacheable
@Cache(
        usage = CacheConcurrencyStrategy.READ_ONLY,
        region = HibernateCacheRegions.COMMENT_REGION
)
@Check(
        name = PostgresConstraints.COMMENT_TABLE_CONSTRAINT,
        constraints = PostgresConstraintsValues.COMMENT_TABLE_CONSTRAINT_VALUE
)
@EntityAnnotations(
        name = PostgreSqlTables.COMMENTS,
        tableName = PostgreSqlTables.COMMENTS,
        keysapceName = PostgreSqlSchema.ENTITIES
)
public final class Comment implements EntityToPostgresConverter {
    @Id
    @GeneratedValue(  strategy = GenerationType.IDENTITY )
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
    private String comment;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column( columnDefinition = "SMALLINT DEFAULT 5" )
    @SuppressWarnings(
            value = """
                    оценка урока от студента
                    по умолчанию будем ставить высшую оценку
                    Интервал оценок 1 - 5
                    """
    )
    private final byte mark = 5;

    @Immutable
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            targetEntity = Student.class
    )
    private Student student;

    @SuppressWarnings( value = "урок к которому оставлен комментарий" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Immutable
    @PartitionKey
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            targetEntity = Lesson.class
    )
    private Lesson lesson;

    public Comment () {}

    @EntityConstructorAnnotation
    public Comment ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Comment.class );
    }
}
