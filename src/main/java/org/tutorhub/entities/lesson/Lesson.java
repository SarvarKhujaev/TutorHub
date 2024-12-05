package org.tutorhub.entities.lesson;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;
import org.tutorhub.annotations.LinksToDocs;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.entities_constants.LessonStatus;

import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.entities.educationTypes.EducationType;
import org.tutorhub.entities.comment.Comment;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Cache;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity( name = PostgreSqlTables.LESSONS )
@Table(
        name = PostgreSqlTables.LESSONS,
        schema = PostgreSqlSchema.ENTITIES
)
@Cacheable
@Cache(
        usage = CacheConcurrencyStrategy.READ_ONLY,
        region = HibernateCacheRegions.LESSON_REGION
)
@EntityAnnotations(
        name = "Lesson",
        tableName = PostgreSqlTables.LESSONS,
        keysapceName = PostgreSqlSchema.ENTITIES
)
public final class Lesson implements EntityToPostgresConverter {
    @Id
    @GeneratedValue(  strategy = GenerationType.IDENTITY )
    private long id;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "lesson_date",
            nullable = false,
            updatable = false,
            columnDefinition = PostgreSqlFunctions.NOW + " + interval '5' day"
    )
    @FutureOrPresent( message = ErrorMessages.DATE_IS_INVALID )
    @SuppressWarnings(
            value = "дата провидения урока по умолчанию ставим на 5 дней вперед"
    )
    private final Date lessonDate = TimeInspector.newDate(
            TimeInspector.newDate().getTime() + 5 * 86400 * 1000
    );

    @Size(
            min = 5,
            max = 50,
            message = ErrorMessages.VALUE_OUT_OF_RANGE
    )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @NotBlank( message = ErrorMessages.NULL_VALUE )
    @NotEmpty( message = ErrorMessages.NULL_VALUE )
    @Column(
            length = 50,
            nullable = false,
            updatable = false,
            columnDefinition = "VARCHAR( 50 )"
    )
    private String lessonName;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToOne(
            targetEntity = EducationType.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    private EducationType educationType;

    @LinksToDocs( links = "https://www.baeldung.com/jpa-default-column-values" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Enumerated( value = EnumType.STRING )
    @PartitionKey
    @Column(
            name = "lesson_status",
            length = 30,
            nullable = false,
            columnDefinition = "VARCHAR( 30 ) DEFAULT 'CREATED'"
    )
    private LessonStatus lessonStatus = LessonStatus.CREATED;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = Comment.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.LESSONS + PostgreSqlTables.COMMENTS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.LESSONS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.LESSONS,
                    nullable = false,
                    updatable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.COMMENTS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.COMMENTS,
                    nullable = false,
                    updatable = false
            )
    )
    @OrderBy( value = "mark ASC" )
    @Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
    @WeakReferenceAnnotation( name = PostgreSqlTables.LESSONS + "_commentList" )
    private final List< Comment > commentList = CollectionsInspector.newList();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            targetEntity = LessonNotes.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.LESSONS + PostgreSqlTables.LESSON_NOTES,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.LESSONS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.LESSONS,
                    nullable = false,
                    updatable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.LESSON_NOTES + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.LESSON_NOTES,
                    nullable = false,
                    updatable = false
            )
    )
    @OrderBy( value = "createdDate ASC" )
    @Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    @WeakReferenceAnnotation( name = PostgreSqlTables.LESSONS + "_lessonNotes" )
    private final List< LessonNotes > lessonNotes = CollectionsInspector.newList();

    public Lesson() {}

    @EntityConstructorAnnotation
    public Lesson ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Lesson.class );
    }
}
