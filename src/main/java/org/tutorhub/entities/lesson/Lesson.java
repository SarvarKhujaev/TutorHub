package org.tutorhub.entities.lesson;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;
import org.tutorhub.annotations.LinksToDocs;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.constans.entities_constants.ErrorMessages;
import org.tutorhub.constans.entities_constants.LessonStatus;

import org.tutorhub.constans.hibernate.HibernateCacheRegions;

import org.tutorhub.entities.comment.Comment;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;
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
    @Immutable
    @PartitionKey
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
    @Immutable
    @PartitionKey
    private String lessonName;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OrderBy( value = "createdDate DESC, comment ASC" )
    @Column( name = "comment_list" )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Comment.class,
            orphanRemoval = true
    )
    @JoinColumn( name = "lesson_id" )
    @SuppressWarnings(
            value = """
                    Hibernate can also cache collections, and the @Cache annotation must be on added to the collection property.
                    If the collection is made of value types (basic or embeddables mapped with @ElementCollection),
                    the collection is stored as such.
                    If the collection contains other entities (@OneToMany or @ManyToMany),
                    the collection cache entry will store the entity identifiers only.
                    """
    )
    @Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private final List< Comment > commentList = CollectionsInspector.newList();

    @LinksToDocs( links = "https://www.baeldung.com/jpa-default-column-values" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Enumerated( value = EnumType.STRING )
    @Column(
            name = "lesson_status",
            nullable = false,
            columnDefinition = "VARCHAR( 255 ) DEFAULT 'CREATED'"
    )
    @PartitionKey
    private LessonStatus lessonStatus = LessonStatus.CREATED;

    public Lesson() {}

    @EntityConstructorAnnotation
    public Lesson ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission(
                Lesson.class,
                instance
        );
    }
}
