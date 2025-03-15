package org.tutorhub.entities.group;

import org.tutorhub.constans.postgres_constants.postgres_constraints_constants.PostgresConstraintsValues;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.constans.hibernate.HibernateNativeNamedQueries;
import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;

import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.entities.educationDirection.EducationDirection;
import org.tutorhub.entities.student.Student;
import org.tutorhub.entities.teacher.Teacher;
import org.tutorhub.entities.lesson.Lesson;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import java.util.Date;
import java.util.List;

@Entity( name = PostgreSqlTables.GROUPS )
@Table(
        name = PostgreSqlTables.GROUPS,
        schema = PostgreSqlSchema.ENTITIES
)
@Cacheable
@Cache(
        usage = CacheConcurrencyStrategy.READ_ONLY,
        region = HibernateCacheRegions.GROUP_REGION
)
@Check( constraints = PostgresConstraintsValues.STUDENTS_COUNT_IN_GROUP_TABLE_CONSTRAINT )
@org.hibernate.annotations.NamedNativeQueries(
        value = {
                @org.hibernate.annotations.NamedNativeQuery(
                        name = HibernateNativeNamedQueries.GET_ALL_GROUPS_FOR_CURRENT_USER_BY_USER_ID,
                        query = HibernateNativeNamedQueries.GET_ALL_GROUPS_FOR_CURRENT_USER_BY_USER_ID_QUERY,
                        timeout = 1,
                        readOnly = true,
                        cacheable = true,
                        flushMode = org.hibernate.annotations.FlushModeType.COMMIT,
                        resultClass = Group.class,
                        comment = """
                                делаем выборку по всем группам
                                чтобы найти те, в которых участвует студент
                                при запросе отправляется параметр student_id
                                """
                )
        }
)
@EntityAnnotations(
        name = "Group",
        tableName = PostgreSqlTables.GROUPS,
        keysapceName = PostgreSqlSchema.ENTITIES
)
public final class Group implements EntityToPostgresConverter {
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
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "VARCHAR( 50 )",
            updatable = false,
            nullable = false,
            unique = true,
            length = 50,
            name = "group_name"
    )
    @Immutable
    @PartitionKey
    private String groupName;

    @SuppressWarnings( value = "максималное количество студентов в одной группе" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "SMALLINT DEFAULT 3",
            nullable = false,
            name = "max_students_number"
    )
    private byte maxStudentsNumber = 3;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            columnDefinition = "SMALLINT DEFAULT 3",
            nullable = false,
            name = "students_number"
    )
    private byte studentsNumber = 3;

    @SuppressWarnings( value = "преподаватель группы" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @PartitionKey
    @ManyToOne(
            targetEntity = Teacher.class,
            cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = PostgreSqlTables.GROUPS + PostgreSqlTables.TEACHERS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.GROUPS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.GROUPS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.TEACHERS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.TEACHERS,
                    nullable = false
            )
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.GROUPS + "_teacher", isCollection = false )
    private Teacher teacher;

    @SuppressWarnings( value = "название направления по которому проводятся занятия" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @ManyToOne(
            targetEntity = EducationDirection.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = PostgreSqlTables.GROUPS + PostgreSqlTables.EDUCATION_DIRECTIONS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.GROUPS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.GROUPS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.EDUCATION_DIRECTIONS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.EDUCATION_DIRECTIONS,
                    nullable = false
            )
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.GROUPS + "_educationDirection", isCollection = false )
    private EducationDirection educationDirection;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Lesson.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.GROUPS + PostgreSqlTables.LESSONS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.GROUPS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.GROUPS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.LESSONS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.LESSONS,
                    nullable = false
            )
    )
    @OrderBy( value = "lessonDate DESC, lessonName ASC" )
    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.GROUPS + "_lessonList" )
    private final List< Lesson > lessonList = CollectionsInspector.newList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Student.class
    )
    @JoinTable(
            name = PostgreSqlTables.GROUPS + PostgreSqlTables.STUDENTS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.GROUPS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.GROUPS,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.STUDENTS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.STUDENTS,
                    nullable = false
            )
    )
    @OrderBy( value = "name DESC, surname ASC" )
    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.READ_WRITE
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.GROUPS + "_lessonList" )
    private final List< Student > studentList = CollectionsInspector.newList();

    public Group () {}

    @EntityConstructorAnnotation
    public Group ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Group.class );
    }
}
