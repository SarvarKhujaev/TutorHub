package org.tutorhub.entities.student;

import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.entities.wishAndPlans.FuturePlan;
import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;

import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.entities.educationTypes.EducationType;
import org.tutorhub.entities.subject.Subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import org.hibernate.annotations.*;

import java.util.Date;
import java.util.List;

@Entity( name = PostgreSqlTables.STUDENTS )
@Table(
        name = PostgreSqlTables.STUDENTS,
        schema = PostgreSqlSchema.ENTITIES
)
@Cacheable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE,
        region = HibernateCacheRegions.STUDENT_REGION
)
@Checks(
        value = {
                @Check( constraints = PostgresConstraintsValues.AGE_CONSTRAINT ),
                @Check( constraints = PostgresConstraintsValues.PHONE_NUMBER_CONSTRAINT )
        }
)
@EntityAnnotations(
        name = PostgreSqlTables.STUDENTS,
        tableName = PostgreSqlTables.STUDENTS,
        keysapceName = PostgreSqlSchema.ENTITIES
)
public final class Student implements EntityToPostgresConverter {
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
    private String name;

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
            name = "father_name"
    )
    private String fatherName;

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 30 )",
            nullable = false,
            length = 30,
            name = "birth_date"
    )
    private String birthDate;

    @SuppressWarnings(
            value = """
                    короткое описание самого студента,
                    которое он заполняет при заполнении анкеты
                    описывает свои предпочтения и планы
                    """
    )
    @Column(
            columnDefinition = "VARCHAR( 200 )",
            length = 200,
            name = "student_short_description"
    )
    private String studentShortDescription;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Subject.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.STUDENTS + PostgreSqlTables.SUBJECT,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.STUDENTS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.STUDENTS,
                    nullable = false,
                    updatable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.SUBJECT + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.SUBJECT,
                    nullable = false,
                    updatable = false
            )
    )
    @OrderBy( value = "name DESC, createdDate DESC" )
    @WeakReferenceAnnotation( name = PostgreSqlTables.STUDENTS + "_subjectList" )
    private final List< Subject > subjectList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            targetEntity = FuturePlan.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.STUDENTS + PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.STUDENTS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.STUDENTS,
                    nullable = false,
                    updatable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.FUTURE_PLANS_AND_WISHES + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.FUTURE_PLANS_AND_WISHES,
                    nullable = false,
                    updatable = false
            )
    )
    @OrderBy( value = "createdDate DESC" )
    @WeakReferenceAnnotation( name = PostgreSqlTables.STUDENTS + "_futurePlanList" )
    private final List< FuturePlan > futurePlanList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = EducationType.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.STUDENTS + PostgreSqlTables.EDUCATION_TYPES,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.STUDENTS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.STUDENTS,
                    nullable = false,
                    updatable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.EDUCATION_TYPES + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.EDUCATION_TYPES,
                    nullable = false,
                    updatable = false
            )
    )
    @OrderBy( value = "name DESC, createdDate DESC" )
    @WeakReferenceAnnotation( name = PostgreSqlTables.STUDENTS + "_educationTypeList" )
    private final List< EducationType > educationTypeList = CollectionsInspector.emptyList();

    public Student () {}

    @EntityConstructorAnnotation
    public Student ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Student.class );
    }
}
