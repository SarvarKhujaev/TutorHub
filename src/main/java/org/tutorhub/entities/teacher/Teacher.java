package org.tutorhub.entities.teacher;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;
import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.entities.educationDirection.EducationDirection;
import org.tutorhub.entities.educationTypes.EducationType;

import org.tutorhub.entities.wishAndPlans.FuturePlan;
import org.tutorhub.entities.lesson.Lesson;
import org.tutorhub.entities.group.Group;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;

import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import jakarta.validation.constraints.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import org.hibernate.annotations.*;

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
@Checks(
        value = {
                @Check( constraints = PostgresConstraintsValues.AGE_CONSTRAINT ),
                @Check( constraints = PostgresConstraintsValues.EXPERIENCE_CONSTRAINT ),
                @Check( constraints = PostgresConstraintsValues.PHONE_NUMBER_CONSTRAINT )
        }
)
@EntityAnnotations(
        name = PostgreSqlTables.TEACHERS,
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

    @Size( min = 1, max = 5, message = ErrorMessages.VALUE_OUT_OF_RANGE )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            nullable = false,
            columnDefinition = "SMALLINT DEFAULT 5"
    )
    private byte rating = 5;

    @SuppressWarnings( value = "количество опыта в годах" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            nullable = false,
            columnDefinition = "SMALLINT"
    )
    private byte experience = 5;

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
    private String email;

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
            min = 13,
            max = 13,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 13 )",
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
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
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
    @JoinTable(
            name = PostgreSqlTables.TEACHERS + PostgreSqlTables.GROUPS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.TEACHERS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.TEACHERS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.GROUPS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.GROUPS,
                    nullable = false
            )
    )
    @OrderBy( value = "groupName DESC, createdDate DESC" )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    @WeakReferenceAnnotation( name = PostgreSqlTables.TEACHERS + "_groupList" )
    private final List< Group > groupList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Lesson.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.TEACHERS + PostgreSqlTables.LESSONS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.TEACHERS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.TEACHERS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.LESSONS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.LESSONS,
                    nullable = false
            )
    )
    @OrderBy( value = "lessonDate ASC, lessonName DESC" )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    @WeakReferenceAnnotation( name = PostgreSqlTables.TEACHERS + "_lessonList" )
    private final List< Lesson > lessonList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            targetEntity = FuturePlan.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.TEACHERS + PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.TEACHERS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.TEACHERS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.FUTURE_PLANS_AND_WISHES + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
                    nullable = false
            )
    )
    @OrderBy( value = "createdDate DESC" )
    @WeakReferenceAnnotation( name = PostgreSqlTables.TEACHERS + "_futurePlanList" )
    private final List< FuturePlan > futurePlanList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = EducationType.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.TEACHERS + PostgreSqlTables.EDUCATION_TYPES,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.TEACHERS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.TEACHERS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.EDUCATION_TYPES + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.EDUCATION_TYPES,
                    nullable = false
            )
    )
    @OrderBy( value = "name DESC, createdDate DESC" )
    @WeakReferenceAnnotation( name = PostgreSqlTables.TEACHERS + "_educationTypeList" )
    private final List< EducationType > educationTypeList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = EducationDirection.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.TEACHERS + PostgreSqlTables.EDUCATION_DIRECTIONS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.TEACHERS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.TEACHERS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.EDUCATION_DIRECTIONS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.EDUCATION_DIRECTIONS,
                    nullable = false
            )
    )
    @OrderBy( value = "directionName DESC, createdDate DESC" )
    @WeakReferenceAnnotation( name = PostgreSqlTables.TEACHERS + "_educationDirectionList" )
    private final List< EducationDirection > educationDirectionList = CollectionsInspector.emptyList();

    public Teacher () {}

    @EntityConstructorAnnotation
    public Teacher ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Teacher.class );
    }
}
