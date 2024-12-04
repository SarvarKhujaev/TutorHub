package org.tutorhub.entities.studyCenter;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.entities.educationDirection.EducationDirection;
import org.tutorhub.entities.educationTypes.EducationType;
import org.tutorhub.entities.address.Address;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Check;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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
@Check( constraints = PostgresConstraintsValues.PHONE_NUMBER_CONSTRAINT )
public final class StudyCenter implements EntityToPostgresConverter {
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

    @Size( min = 1, message = ErrorMessages.VALUE_OUT_OF_RANGE )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            nullable = false,
            columnDefinition = "SMALLINT DEFAULT 1"
    )
    private byte age = 1;

    @Size( min = 1, max = 5, message = ErrorMessages.VALUE_OUT_OF_RANGE )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            nullable = false,
            columnDefinition = "SMALLINT DEFAULT 5"
    )
    private byte rating = 5;

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

    @NotNull
    @OneToOne(
            targetEntity = Address.class,
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Address address;

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
            columnDefinition = "VARCHAR( 13 )",
            nullable = false,
            unique = true,
            length = 13,
            name = "phone_number"
    )
    @PartitionKey
    private String phoneNumber;

    @SuppressWarnings( value = "список курсов центра" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Course.class,
            orphanRemoval = true
    )
    @OrderBy( value = "name DESC, createdDate DESC" )
    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.READ_ONLY
    )
    @JoinTable(
            name = PostgreSqlTables.STUDY_CENTER + PostgreSqlTables.COURSES,
            joinColumns = @JoinColumn( name = PostgreSqlTables.STUDY_CENTER + StringOperations.ENTITY_ID ),
            inverseJoinColumns = @JoinColumn( name = PostgreSqlTables.COURSES + StringOperations.ENTITY_ID )
    )
    private List< Course > courseList = CollectionsInspector.emptyList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = PostgreSqlTables.EDUCATION_TYPES,
            targetEntity = EducationType.class,
            orphanRemoval = true
    )
    @OrderBy( value = "name DESC, createdDate DESC" )
    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE
    )
    private final List< EducationType > educationTypeList = CollectionsInspector.emptyList();

    @SuppressWarnings( value = "название направлений по которым проводятся занятия" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST,
            mappedBy = PostgreSqlTables.EDUCATION_DIRECTIONS,
            targetEntity = EducationDirection.class,
            orphanRemoval = true
    )
    @OrderBy( value = "directionName DESC, createdDate DESC" )
    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.READ_ONLY
    )
    private List< EducationDirection > educationDirections = CollectionsInspector.emptyList();

    public StudyCenter () {}

    @EntityConstructorAnnotation
    public StudyCenter( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, StudyCenter.class );
    }
}
