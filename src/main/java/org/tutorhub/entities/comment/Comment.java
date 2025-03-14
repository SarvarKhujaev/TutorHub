package org.tutorhub.entities.comment;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;

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
@Check( constraints = PostgresConstraintsValues.COMMENT_TABLE_CONSTRAINT_VALUE )
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

    @Column(
            length = 200,
            nullable = false,
            columnDefinition = "VARCHAR( 200 )"
    )
    private String comment;

    @Size( min = 1, max = 5 )
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
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            targetEntity = Student.class,
            orphanRemoval = true
    )
    private Student student;

    @SuppressWarnings( value = "урок к которому оставлен комментарий" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Immutable
    @PartitionKey
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            targetEntity = Lesson.class,
            orphanRemoval = true
    )
    private Lesson lesson;

    public Comment () {}

    @EntityConstructorAnnotation
    public Comment ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Comment.class );
    }
}
