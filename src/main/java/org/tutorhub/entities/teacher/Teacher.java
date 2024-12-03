package org.tutorhub.entities.teacher;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;
import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraints;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.inspectors.AnnotationInspector;
import org.tutorhub.interfaces.database.EntityToPostgresConverter;
import org.tutorhub.entities.group.Group;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.CollectionsInspector;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Check;

import java.util.Date;
import java.util.List;

@Entity( name = PostgreSqlTables.TEACHERS )
@Table(
        name = PostgreSqlTables.TEACHERS,
        schema = PostgreSqlSchema.ENTITIES
)
@Cacheable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE,
        region = HibernateCacheRegions.TEACHER_REGION
)
@Check(
        name = PostgresConstraints.TEACHER_TABLE_CONSTRAINT,
        constraints = PostgresConstraintsValues.TEACHER_TABLE_CONSTRAINT_VALUE
)
@Check(
        name = PostgresConstraints.TEACHER_TABLE_PHONE_NUMBER_CONSTRAINT,
        constraints = PostgresConstraintsValues.PHONE_NUMBER_CONSTRAINT_VALUE
)
@EntityAnnotations(
        name = "Teacher",
        tableName = PostgreSqlTables.TEACHERS,
        keysapceName = PostgreSqlSchema.ENTITIES
)
public final class Teacher implements EntityToPostgresConverter {
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

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            nullable = false,
            columnDefinition = "SMALLINT DEFAULT 18"
    )
    private byte age = 18;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 50 )",
            nullable = false,
            length = 50
    )
    private String name;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @Email( message = ErrorMessages.WRONG_EMAIL )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    @PartitionKey
    private String email;

    @Size(
            min = 13,
            max = 13,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 13 ) DEFAULT '+9989771221' || random_between( 10, 100 )",
            nullable = false,
            unique = true,
            length = 13,
            name = "phone_number"
    )
    @PartitionKey
    private String phoneNumber;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 50 )",
            nullable = false,
            length = 50
    )
    private String surname;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 50 )",
            nullable = false,
            length = 50,
            name = "birth_date"
    )
    private String birthDate;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 50 )",
            nullable = false,
            length = 50,
            name = "father_name"
    )
    private String fatherName;

    @SuppressWarnings(
            value = """
                    короткое описание самого преподавателя,
                    которое он заполняет при заполнении анкеты
                    описывает свои предпочтения и планы
                    """
    )
    @Size(
            max = 200,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @Column(
            columnDefinition = "VARCHAR( 200 )",
            length = 200,
            name = "teacher_short_description"
    )
    private String teacherShortDescription;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Group.class,
            orphanRemoval = true
    )
    @Column( name = "group_list" )
    @OrderBy( value = "groupName DESC, createdDate DESC" )
    @JoinColumn( name = "teacher_id" )
    @SuppressWarnings(
            value = """
                    Hibernate can also cache collections, and the @Cache annotation must be on added to the collection property.
                    If the collection is made of value types (basic or embeddables mapped with @ElementCollection),
                    the collection is stored as such.
                    If the collection contains other entities (@OneToMany or @ManyToMany),
                    the collection cache entry will store the entity identifiers only.
                    """
    )
    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE
    )
    private final List< Group > groupList = CollectionsInspector.emptyList();

    public Teacher () {}

    @EntityConstructorAnnotation
    public Teacher ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Teacher.class );
    }
}
